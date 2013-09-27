package com.evrasoft.sling.util;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.evrasoft.jcr.cnd.NodeDefinition;
import com.evrasoft.jcr.cnd.NodeTypeDefinition;
import com.evrasoft.jcr.cnd.PropertyDefinition;
import com.evrasoft.sling.slingContent.Member;
import com.evrasoft.sling.slingContent.Node;

public class CndUtil {

	public static NodeTypeDefinition getNodePrimaryType(Node node) {
		EList<Member> members = node.getMembers();
		for (Member member : members) {
			if (member.getPrimaryType() != null) {
				return member.getPrimaryType();
			}
		}
		return null;
	}

	public static List<PropertyDefinition> getEffectivePropertyDefinitions(
			NodeTypeDefinition nodeType) {

		final LinkedList<PropertyDefinition> propertyDefs = new LinkedList<PropertyDefinition>();
		PropertyDefinitionAcceptor acceptor = new PropertyDefinitionAcceptor() {

			@Override
			public boolean cancel() {
				return false;
			}

			@Override
			public void accept(NodeTypeDefinition nodeTypeDefinition,
					PropertyDefinition propertyTypeDefinition) {

				if (!propertyTypeDefinition.getName().equals("*")) {
					propertyDefs.add(propertyTypeDefinition);
				}

			}

			@Override
			public void accept(NodeTypeDefinition nodeTypeDefinition, NodeDefinition nodeDefinition) {
				// ignore
			}
		};
		traverseNodeTypeDefinitionHierarchy(nodeType, acceptor);
		return propertyDefs;
	}

	public static List<NodeDefinition> getEffectiveChildNodeDefinitions(NodeTypeDefinition nodeType) {

		final LinkedList<NodeDefinition> nodeDefs = new LinkedList<NodeDefinition>();
		PropertyDefinitionAcceptor acceptor = new PropertyDefinitionAcceptor() {

			@Override
			public boolean cancel() {
				return false;
			}

			@Override
			public void accept(NodeTypeDefinition nodeTypeDefinition,
					PropertyDefinition propertyTypeDefinition) {
			}

			@Override
			public void accept(NodeTypeDefinition nodeTypeDefinition, NodeDefinition nodeDefinition) {
				if (!nodeDefinition.getName().equals("*")) {
					nodeDefs.add(nodeDefinition);
				}
			}
		};
		traverseNodeTypeDefinitionHierarchy(nodeType, acceptor);
		return nodeDefs;
	}

	public static void traverseNodeTypeDefinitionHierarchy(NodeTypeDefinition nodeTypeDefinition,
			PropertyDefinitionAcceptor acceptor) {

		EList<NodeTypeDefinition> declaredSupertypeNames = nodeTypeDefinition
				.getDeclaredSupertypeNames();
		for (NodeTypeDefinition superNodeTypeDefinition : declaredSupertypeNames) {
			if (acceptor.cancel()) {
				return;
			}
			traverseNodeTypeDefinitionHierarchy(superNodeTypeDefinition, acceptor);
		}

		EList<NodeDefinition> declaredChildNodeDefinitions = nodeTypeDefinition
				.getDeclaredChildNodeDefinitions();
		for (NodeDefinition nodeDefinition : declaredChildNodeDefinitions) {
			if (acceptor.cancel()) {
				return;
			}
			acceptor.accept(nodeTypeDefinition, nodeDefinition);
		}

		EList<PropertyDefinition> declaredPropertyDefinitions = nodeTypeDefinition
				.getDeclaredPropertyDefinitions();
		for (PropertyDefinition propertyDefinition : declaredPropertyDefinitions) {
			if (acceptor.cancel()) {
				return;
			}
			acceptor.accept(nodeTypeDefinition, propertyDefinition);
		}

	}

	public static boolean isPropertyAllowed(NodeTypeDefinition nodePrimaryType,
			final String propertyName) {
		final boolean[] propertyAllowedRef = { false };
		PropertyDefinitionAcceptor acceptor = new PropertyDefinitionAcceptor() {

			private boolean cancel = false;

			@Override
			public boolean cancel() {
				return cancel;
			}

			@Override
			public void accept(NodeTypeDefinition nodeTypeDefinition,
					PropertyDefinition propertyTypeDefinition) {

				if (propertyTypeDefinition.getName().equals("*")) {
					propertyAllowedRef[0] = true;
					cancel = true;
				}

				if (propertyTypeDefinition.getName().equals(propertyName)) {
					propertyAllowedRef[0] = true;
					cancel = true;
				}

			}

			@Override
			public void accept(NodeTypeDefinition nodeTypeDefinition, NodeDefinition nodeDefinition) {
			}
		};
		traverseNodeTypeDefinitionHierarchy(nodePrimaryType, acceptor);

		return propertyAllowedRef[0];
	}

}
