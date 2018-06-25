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
 * and abstracts away the process of querying for SEARCH.
 *
 * Can return the DISHES associated with TAG VALUES.
 */
public class TagHandler {

  // maps tag categories to their associated Tag object
  private HashMap<String, Tag> tagsByType;

  public TagHandler(HashMap<String, Tag> tagsByType,
                  HashMap<UUID, HashMap<String, Set<String>>> tagsByDish) {
    this.tagsByType = tagsByType;
    // TODO: REMOVE? this.tagsByDish = tagsByDish;
  }

  public void setTag(UUID id, String type, Set<String> values) {

  }

  /**
   * Retrieves the Tag object associated with a particular category.
   * i.e. "cuisine", "restriction", etc.
   * Good for Search querying.
   * @method getTagsOfType
   * @param  type          name of the tag category
   * @return               appropriate Tag object
   */
  public Tag getTagsOfType(String type) {
    return this.tagsByType.get(type);
  }

  /**
   * Puts the Dish into all the correct Tag objects.
   * Puts all the user tags into the Dish object itself.
   * @method setTags
   * @param  id          dish ID user entered tags for
   * @param  userTags    {tagType : {tagValues}} || i.e. {restrictions: {vegan, vegetarian}}
   */
  public void setTagsForDish(UUID id,  HashMap<String, Set<String> userTags) {
    assignDishToTags(id, userTags);
    assignTagsToDish(id, userTags);
  }

  /**
   * Goes through all userTags, and puts the Dish into the appropriate Tag objects.
   * @method assignDishToTags
   * @param  id               dish id we're placing into the Tag objects
   * @param  userTags         all the user-given tags
   */
  private void assignDishToTags(UUID id,  HashMap<String, Set<String> userTags) {
    for (String tagType : userTags) {
      Tag tag = getTagsOfType(tagType);
      Set<String> tagValues = userTags.get(tagType);
      tag.addDishToTagValue(id, userTags.get(tagType));
    }
  }

  private void assignTagsToDish(UUID id,  HashMap<String, Set<String> userTags) {
      // DishStore should be calling this?? question mark??
  }

}
