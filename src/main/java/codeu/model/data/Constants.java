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

public class Constants {

  /* Cuisine Types Constants*/
  enum CuisineType {
    ASIAN("Asian"), AMERICAN("American"), MEXICAN("Mexican"), EUROPEAN("European");

    private String type;

    CuisineType(String type) {
      this.type = type;
    }

    public String getType() {
      return this.type;
    }
  }

  /* Dish Type Constants */
  enum DishType {


    private String type;

    CuisineType(String type) {
      this.type = type;
    }

    public String getType() {
      return this.type;
    }
  }
  /*  Dietary Restriction Constants */

  /* Location Constants */
}
static final const String // for all the constants we’ll be using to check things against
cuisine types (Asian, American, etc.)
dish types (Noodles, etc.)
restrictions (Vegetarian, Vegan, etc.)
