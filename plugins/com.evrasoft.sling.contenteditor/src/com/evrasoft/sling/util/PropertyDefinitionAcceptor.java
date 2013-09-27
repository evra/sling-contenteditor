package com.evrasoft.sling.util;

import com.evrasoft.jcr.cnd.NodeDefinition;
import com.evrasoft.jcr.cnd.NodeTypeDefinition;
import com.evrasoft.jcr.cnd.PropertyDefinition;

public interface PropertyDefinitionAcceptor {

	public void accept(NodeTypeDefinition nodeTypeDefinition,
			PropertyDefinition propertyTypeDefinition);

	public void accept(NodeTypeDefinition nodeTypeDefinition, NodeDefinition nodeDefinition);

	public boolean cancel();

}
