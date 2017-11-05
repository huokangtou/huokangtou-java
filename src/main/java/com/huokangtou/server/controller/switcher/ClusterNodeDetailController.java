package com.huokangtou.server.controller.switcher;

import com.asiainno.support.switcher.api.generate.Switcher;
import com.asiainno.support.switcher.api.generate.SwitcherServiceRPC;
import com.huokangtou.server.model.ClusterNodeDetail;
import com.huokangtou.server.service.ClusterNodeDetailService;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/switcher/cluster_node_detail")
public class ClusterNodeDetailController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterNodeDetailController.class);
    @Autowired
    private ClusterNodeDetailService clusterNodeDetailService;

    /**
     * 显示集群节点明细列表
     *
     * @param cluster
     * @param node
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listAction(@RequestParam(value = "cluster", required = false) String cluster, @RequestParam(value = "node", required = false) String node, Model model) {
        Set<String> clusters = clusterNodeDetailService.getAllCluster();
        model.addAttribute("clusters", clusters);
        if (StringUtils.isNotEmpty(cluster)) {
            model.addAttribute("currentCluster", cluster);
            List<ClusterNodeDetail> clusterNodeDetails = clusterNodeDetailService.getClusterNodeDetailsByCluster(cluster);
            Set<String> nodes = new HashSet<String>();
            for (ClusterNodeDetail cnd : clusterNodeDetails) {
                nodes.add(cnd.getNode());
            }
            model.addAttribute("nodes", nodes);
            if (StringUtils.isNotEmpty(node)) {
                model.addAttribute("currentNode", node);
                ClusterNodeDetail clusterNodeDetail = clusterNodeDetailService.getClusterNodeDetailByClusterAndNode(cluster, node);
                if (clusterNodeDetail != null) {
                    try {
                        NettyTransceiver client = new NettyTransceiver(new InetSocketAddress(clusterNodeDetail.getIp(), clusterNodeDetail.getPort()), 5000L);
                        SwitcherServiceRPC switcherService = (SwitcherServiceRPC) SpecificRequestor.getClient(SwitcherServiceRPC.class, client);
                        List<Switcher> switcheres = switcherService.list();
                        model.addAttribute("switcheres", switcheres);
                    } catch (Throwable e) {
                        LOGGER.error(e.getMessage());
                    }
                }
            } else {
                model.addAttribute("clusterNodeDetails", clusterNodeDetails);
            }
        }
        return "/switcher/cluster_node_detail/list";
    }

    /**
     * 注册新的集群节点明细表单
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "register_form", method = RequestMethod.GET)
    public String registerFormAction(Model model) {
        model.addAttribute("ClusterNodeDetail", new ClusterNodeDetail());
        return "/switcher/cluster_node_detail/register_form";
    }

    /**
     * 注册新的集群节点明细
     *
     * @param clusterNodeDetail
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "do_register", method = RequestMethod.POST)
    public String doRegisterAction(@Valid ClusterNodeDetail clusterNodeDetail, BindingResult bindingResult) {
        boolean ok = true;
        if (bindingResult.hasErrors()) {
            ok = false;
        } else {
            clusterNodeDetailService.insertSelective(clusterNodeDetail);
        }
        if (ok) {
            return String.format("redirect:/switcher/cluster_node_detail/list?cluster=%s", clusterNodeDetail.getCluster());
        } else {
            return "redirect:/switcher/cluster_node_detail/register_form";
        }
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String deleteAction (@RequestParam(value = "id", required = false) int id, @RequestParam(value = "cluster", required = false) String cluster, Model model) {
       clusterNodeDetailService.deleteByPrimaryKey(id);
       model.addAttribute("currentCluster", cluster);
       return String.format("redirect:/switcher/cluster_node_detail/list?cluster=%s", cluster);
    }
}
