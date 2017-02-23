/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage;

/**
 * This interface represents what action a developer wants the MultiPageDisplay to run when
 * the user requests for a new page to be created by for example clicking the new tab button,
 * hitting Ctrl+T, or programmatically by calling multiPageDisplay.addPage()
 */
public interface NewPageAction {
    void onAddPage(MultiPageDisplay multiPageDisplay);
}
