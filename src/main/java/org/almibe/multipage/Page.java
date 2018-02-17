/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.almibe.multipage;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;

import java.util.Map;
import java.util.concurrent.Callable;

public interface Page {
    Node content();
    ReadOnlyStringProperty title();
    Node icon();
    Callable<Boolean> allowClose();
    Map<KeyCombination, Runnable> accelerators();
}
