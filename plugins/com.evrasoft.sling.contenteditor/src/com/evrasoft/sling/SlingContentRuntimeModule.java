/*
 * generated by Xtext
 */
package com.evrasoft.sling;


/**
 * Use this class to register components to be used at runtime / without the
 * Equinox extension registry.
 */
public class SlingContentRuntimeModule extends com.evrasoft.sling.AbstractSlingContentRuntimeModule {

	@Override
	public Class<? extends org.eclipse.xtext.scoping.IScopeProvider> bindIScopeProvider() {
		return com.evrasoft.sling.scoping.SlingContentScopeProvider2.class;
	}

}