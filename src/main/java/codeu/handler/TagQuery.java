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

package codeu.model.data.query;

import codeu.controller.handler.Tag;
import java.util.UUID;
import java.util.HashMap;

/**
 * Wrapper class that loads information from Data Store,
 * handles the setting & getting of information for Tags,
 * and abstracts the process of querying.
 */
public class TagQuery {

  // maps tag categories to their associated Tag object
  private HashMap<String, Tag> tagsByType;
  // maps dishes to all their tags, organized by the tag category/type
  private HashMap<UUID, HashMap<String, Set<String>>> tagsByDish;

  public TagQuery(HashMap<String, Tag> tagsByType,
                  HashMap<UUID, HashMap<String, Set<String>>> tagsByDish) {
    this.tagsByType = tagsByType;
    this.tagsByDish = tagsByDish;
  }

  public void setTag(UUID id, String type, Set<String> values) {

  }

  /**
   * Retrieves the Tag object associated with a particular category,
   * i.e. "cuisine", "restriction", etc.
   * @method getTagsOfType
   * @param  type          name of the tag category
   * @return               appropriate Tag object
   */
  public Tag getTagsOfType(String type) {
    return this.tagsByType.get(type);
  }

  /**
   * Retrieves all the tags for a particular Dish.
   * @method getTagsForDish
   * @param  id             id of the dish we want all tags of
   * @return                returns {tag type : {tag values}}
   */
  public HashMap<String, Set<String>> getTagsForDish(UUID id) {
    return this.tagsByDish.get(id);
  }

}
