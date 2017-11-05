package com.huokangtou.server.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huokangtou.server.mapper.ClusterNodeDetailMapper;
import com.huokangtou.server.model.ClusterNodeDetail;
import java.util.HashSet;
import java.util.Set;

@Service
public class ClusterNodeDetailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterNodeDetailService.class);
    @Autowired
    private ClusterNodeDetailMapper clusterNodeDetailMapper;

    public List<ClusterNodeDetail> selectAllSelective(ClusterNodeDetail service) {
        return clusterNodeDetailMapper.selectAllSelective(service);
    }

    public ClusterNodeDetail selectSelective(ClusterNodeDetail service) {
        return clusterNodeDetailMapper.selectSelective(service);
    }

    public List<ClusterNodeDetail> getClusterNodeDetailsByCluster (String cluster) {
        ClusterNodeDetail clusterNodeDetail = new ClusterNodeDetail();
        clusterNodeDetail.setCluster(cluster);
        return selectAllSelective(clusterNodeDetail);
    }

    public ClusterNodeDetail getClusterNodeDetailByClusterAndNode (String cluster, String node) {
        ClusterNodeDetail clusterNodeDetail = new ClusterNodeDetail();
        clusterNodeDetail.setCluster(cluster);
        clusterNodeDetail.setNode(node);
        return selectSelective(clusterNodeDetail);
    }

    public Set<String> getAllCluster () {
        List<ClusterNodeDetail> clusterNodeDetails = clusterNodeDetailMapper.selectAllSelective(new ClusterNodeDetail());
        Set<String> clusters = new HashSet<String>();
        for (ClusterNodeDetail clusterNodeDetail : clusterNodeDetails) {
            clusters.add(clusterNodeDetail.getCluster());
        }
        return clusters;
    }

    public int insertSelective (ClusterNodeDetail clusterNodeDetail) {
        return clusterNodeDetailMapper.insertSelective(clusterNodeDetail);
    }

    public int updateByPrimaryKeySelective (ClusterNodeDetail clusterNodeDetail) {
        return clusterNodeDetailMapper.updateByPrimaryKeySelective(clusterNodeDetail);
    }

    public int deleteByPrimaryKey (int id) {
        return clusterNodeDetailMapper.deleteByPrimaryKey(id);
    }
}
