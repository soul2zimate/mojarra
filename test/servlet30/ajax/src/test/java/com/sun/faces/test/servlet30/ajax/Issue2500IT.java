/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.test.servlet30.ajax; 

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class Issue2500IT {

    /**
     * Stores the web URL.
     */
    private String webUrl;
    /**
     * Stores the web client.
     */
    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }


    // ------------------------------------------------------------ Test Methods

    /**
     * This test verifies that an attribute nameed 'value' can be successfully updated
     * from a partial response (over Ajax). 
     */
    @Test
    public void testNoDuplicateViewState() throws Exception {
        HtmlPage page = webClient.getPage(webUrl+"faces/dupViewState.xhtml");
        HtmlSubmitInput button1 = (HtmlSubmitInput)page.getElementById("btn5");
        page = button1.click();
        webClient.waitForBackgroundJavaScript(60000);
        HtmlSubmitInput button2 = (HtmlSubmitInput)page.getElementById("button1");
        page = button2.click();
        webClient.waitForBackgroundJavaScript(60000);
        page = button2.click();
        webClient.waitForBackgroundJavaScript(60000);
        assertTrue(page.asXml().contains("jakarta.faces.ViewState Has One Value"));
    }
}
