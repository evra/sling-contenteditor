package com.evrasoft.sling.scoping;

import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.Scopes;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;

import com.evrasoft.jcr.cnd.NodeDefinition;
import com.evrasoft.jcr.cnd.NodeTypeDefinition;
import com.evrasoft.jcr.cnd.PropertyDefinition;
import com.evrasoft.sling.slingContent.Node;
import com.evrasoft.sling.util.CndUtil;

public class SlingContentScopeProvider2 extends AbstractDeclarativeScopeProvider {

	public IScope scope_Member_propertyName(Node ctx, EReference propertyRef) {

		Node parentNode = ctx;

		if (parentNode != null) {
			NodeTypeDefinition nodePrimaryType = CndUtil.getNodePrimaryType(parentNode);
			if (nodePrimaryType != null && !nodePrimaryType.eIsProxy()) {

				List<PropertyDefinition> effectivePropertyDefinitions = CndUtil
						.getEffectivePropertyDefinitions(nodePrimaryType);
				return Scopes.scopeFor(effectivePropertyDefinitions);

			}
		}
		return IScope.NULLSCOPE;
	}

	public IScope scope_Member_subNodeName(Node ctx, EReference propertyRef) {

		Node parentNode = ctx;

		if (parentNode != null) {
			NodeTypeDefinition nodePrimaryType = CndUtil.getNodePrimaryType(parentNode);
			if (nodePrimaryType != null && !nodePrimaryType.eIsProxy()) {

				List<NodeDefinition> effectiveChildNodeDefinitions = CndUtil
						.getEffectiveChildNodeDefinitions(nodePrimaryType);
				return Scopes.scopeFor(effectiveChildNodeDefinitions);

			}
		}
		return IScope.NULLSCOPE;
	}

}
