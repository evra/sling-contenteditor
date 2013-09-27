package com.evrasoft.sling.ui.contentassist;

public class SlingContentProposalProvider2 extends
		AbstractSlingContentProposalProvider {

	// @Inject
	// IScopeProvider provider;
	//
	// @Override
	// public void completeMember_PropertyName(EObject model, Assignment
	// assignment,
	// ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
	//
	// IScope scope = provider.getScope(model,
	// SlingContentPackage.eINSTANCE.getMember_Name());
	//
	// // // customer is contained in Employee
	// // Employee employeer = (Employee) model;
	// //
	// // // scope contains only customer
	// for (IEObjectDescription element : scope.getAllElements()) {
	//
	// // we know that they are customer
	// // Customer customer = (Customer) element.element();
	// //
	// // // check existing customers and prefix
	// // if (!employeer.getCustomer().contains(customer)
	// // && customer.getName().startsWith(context.getPrefix())) {
	// // // create new proposal
	// // ICompletionProposal completionProposal =
	// // createCompletionProposal(
	// // customer, customer.getName(), context);
	// // // register proposal
	// // acceptor.accept(completionProposal);
	// // }
	//
	// }
	// }
}