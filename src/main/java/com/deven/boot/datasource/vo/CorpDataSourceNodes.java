package com.deven.boot.datasource.vo;


import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 企业库datasource上下文
 */
public class CorpDataSourceNodes {
    private List<CorpDataSourceNode> nodes;

    public List<CorpDataSourceNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<CorpDataSourceNode> nodes) {
        this.nodes = nodes;
    }

    public void addNode(CorpDataSourceNode node) {

        if (CollectionUtils.isNotEmpty(this.nodes) && node != null) {
            this.nodes.add(node);
        }
    }

    public boolean containsNode(CorpDataSourceNode node) {

        if (CollectionUtils.isNotEmpty(this.nodes) && node != null) {
            String nodeName = node.getNode();
            Set<String> nodeNameSet = this.nodes.stream().map(CorpDataSourceNode::getNode).collect(Collectors.toSet());
            return nodeNameSet.contains(nodeName);
        }
        return false;
    }
}
