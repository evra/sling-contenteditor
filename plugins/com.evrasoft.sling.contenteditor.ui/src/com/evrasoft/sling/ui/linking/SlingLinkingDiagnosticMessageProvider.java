package com.evrasoft.sling.ui.linking;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

import com.evrasoft.jcr.cnd.NodeTypeDefinition;
import com.evrasoft.sling.slingContent.Member;
import com.evrasoft.sling.slingContent.Node;
import com.evrasoft.sling.slingContent.SlingContentPackage;
import com.evrasoft.sling.util.CndUtil;

public class SlingLinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider {

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(final ILinkingDiagnosticContext context) {
		EObject elementContainer = context.getContext().eContainer();
		EReference eReference = context.getReference();
		if (elementContainer instanceof Node
				&& eReference.getFeatureID() == SlingContentPackage.MEMBER__PROPERTY_NAME) {
			Node parentNode = (Node) elementContainer;

			EList<Member> members = parentNode.getMembers();
			NodeTypeDefinition nodePrimaryType = null;
			for (Member member : members) {
				NodeTypeDefinition primaryType = member.getPrimaryType();
				if (primaryType != null && !primaryType.eIsProxy()) {
					nodePrimaryType = primaryType;
					break;
				}
			}
			String nodePrimaryTypeName;
			if (nodePrimaryType == null) {
				// TODO resolve nt:unstructured NTD
				nodePrimaryTypeName = "unknown";
			} else {
				nodePrimaryTypeName = nodePrimaryType.getName();
			}
			if (nodePrimaryType != null && isValidProperty(nodePrimaryType, context.getLinkText())) {
				return null;
			}

			return new DiagnosticMessage("NodeType " + nodePrimaryTypeName
					+ " does not define property " + context.getLinkText(), Severity.ERROR,
					"undefined.property", context.getLinkText());

		}
		return super.getUnresolvedProxyMessage(context);
	}

	private boolean isValidProperty(NodeTypeDefinition nodePrimaryType, String linkText) {
		return CndUtil.isPropertyAllowed(nodePrimaryType, linkText);
	}
}
