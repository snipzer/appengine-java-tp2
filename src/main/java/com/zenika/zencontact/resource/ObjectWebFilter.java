package com.zenika.zencontact.resource;

import com.googlecode.objectify.ObjectifyFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = ("/*"))
public class ObjectWebFilter extends ObjectifyFilter {
}
