/*
 Copyright (c) 2014, TomasMikula
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.almibe.multipage.skins;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MappedList<E, F> extends TransformationList<E, F> {

    public static <E, F> ObservableList<F> map(ObservableList<? extends E> sourceList, Function<? super E, ? extends F> f) {
        return new MappedList<>(sourceList, f);
    }

    private final Function<? super F, ? extends E> mapper;

    public MappedList(ObservableList<? extends F> source, Function<? super F, ? extends E> mapper) {
        super(source);
        this.mapper = mapper;
    }

    @Override
    public int getSourceIndex(int index) {
        return index;
    }

    @Override
    public E get(int index) {
        return mapper.apply(getSource().get(index));
    }

    @Override
    public int size() {
        return getSource().size();
    }

    @Override
    protected void sourceChanged(Change<? extends F> c) {
        fireChange(new Change<E>(this) {

            @Override
            public boolean wasAdded() {
                return c.wasAdded();
            }

            @Override
            public boolean wasRemoved() {
                return c.wasRemoved();
            }

            @Override
            public boolean wasReplaced() {
                return c.wasReplaced();
            }

            @Override
            public boolean wasUpdated() {
                return c.wasUpdated();
            }

            @Override
            public boolean wasPermutated() {
                return c.wasPermutated();
            }

            @Override
            public int getPermutation(int i) {
                return c.getPermutation(i);
            }

            @Override
            protected int[] getPermutation() {
                // This method is only called by the superclass methods
                // wasPermutated() and getPermutation(int), which are
                // both overriden by this class. There is no other way
                // this method can be called.
                throw new AssertionError("Unreachable code");
            }

            @Override
            public List<E> getRemoved() {
                ArrayList<E> res = new ArrayList<>(c.getRemovedSize());
                for(F e: c.getRemoved()) {
                    res.add(mapper.apply(e));
                }
                return res;
            }

            @Override
            public int getFrom() {
                return c.getFrom();
            }

            @Override
            public int getTo() {
                return c.getTo();
            }

            @Override
            public boolean next() {
                return c.next();
            }

            @Override
            public void reset() {
                c.reset();
            }
        });
    }
}