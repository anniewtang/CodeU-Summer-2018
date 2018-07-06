// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.basic;

import codeu.model.data.Tag;
import codeu.orm.TagORM;

import codeu.model.store.persistence.PersistentStorageAgent;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.UUID;


/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class TagStore {

    /**
     * Singleton instance of TagStore.
     */
    private static TagStore instance;

    /**
     * Returns the singleton instance of TagStore that should be shared between all servlet
     * classes. Do not call this function from a test; use getTestInstance() instead.
     */
    public static TagStore getInstance() {
        if (instance == null) {
            instance = new TagStore(PersistentStorageAgent.getInstance());
        }
        return instance;
    }

    /**
     * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
     *
     * @param persistentStorageAgent a mock used for testing
     */
    public static TagStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
        return new TagStore(persistentStorageAgent);
    }

    /**
     * The PersistentStorageAgent responsible for loading Dishes from and saving Dishes
     * to Datastore.
     */
    private PersistentStorageAgent persistentStorageAgent;

    /**
     * The in-memory store of DishQuery.
     */
    private TagORM orm;

    /**
     * This class is a singleton, so its constructor is private. Call getInstance() instead.
     */
    private TagStore(PersistentStorageAgent persistentStorageAgent) {
        this.persistentStorageAgent = persistentStorageAgent;
        // TODO: FIGURE THIS FOLLOWING CODE OUT
        //    orm = new TagORM();
    }

    /**
     * Returns the Tag object associated with the tagType category
     */
    public Tag getTagForType(String tagType) {
        return orm.getTagForType(tagType);
    }

    /**
     * Updates existing Tag objects with new user tags
     */
    public void updateTags(UUID dishID, Map<String, Set<String>> userTags) {
        Set<Tag> updatedTags = orm.updateTags(dishID, userTags);
        for (Tag tag : updatedTags) {
            persistentStorageAgent.writeThrough(tag);
        }
    }

    /**
     * Sets the Handler object (contains Tags + querying methods) in the TagStore.
     */
    public void setTags(TagORM orm) {
        this.orm = orm;
    }
}
