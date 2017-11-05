package com.huokangtou.server.mapper;

import com.huokangtou.server.model.ClusterNodeDetail;
import java.util.List;

public interface ClusterNodeDetailMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ClusterNodeDetail record);

    int insertSelective(ClusterNodeDetail record);

    ClusterNodeDetail selectByPrimaryKey(Integer id);

    ClusterNodeDetail selectSelective(ClusterNodeDetail record);

    List<ClusterNodeDetail> selectAllSelective(ClusterNodeDetail record);

    int updateByPrimaryKeySelective(ClusterNodeDetail record);

    int updateByPrimaryKey(ClusterNodeDetail record);
}
