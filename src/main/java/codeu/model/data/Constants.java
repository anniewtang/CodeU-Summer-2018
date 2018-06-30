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
    // Regional
    ASIAN("Asian"), AMERICAN("American"), MEXICAN("Mexican"), EUROPEAN("European"),
    // Top Ethnic Food preferences in the USA (via Google)
    CHINESE("Chinese"), MEXICAN("Mexican"), ITALIAN("Italian"), JAPANESE("Japanese"),
    GREEK("Greek"), FRENCH("French"), THAI("Thai"), SPANISH("Spanish"), INDIAN("Indian"),
    MEDITERRAENEAN("Mediterraenean"), CAJUN("Cajun"), SOUL("Soul"), GERMAN("German"),
    KOREAN("Korean"), VIETNAMESE("Vietnamese"), TURKISH("Turkish"), CARIBBEAN("Caribbean");

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
    // Main Categories
    APPETIZER("Appetizer"), ENTREE("Entree"), DESSERT("Dessert"),
    BREAKFAST("Breakfast"), LUNCH("Lunch"), DINNER("Dinner"),
    SWEET("Sweet"), SAVORY("Savory"), SALTY("Salty"), SOUR("Sour"),
    // Specific Categories
    SOUP("Soup"), BREAD("Bread"), PORRIDGE("Porridge"), EGGS("Eggs"), WAFFLES("Waffles"),
    PANCAKES("Pancakes"), COFFEE("Coffee"), OATMEAL("Oatmeal"), YOGURT("Yogurt"), BAGEL("Bagel")
    NOODLE("Noodle"), PASTA("Pasta"), RICE("Rice"), BURGER("Burger"), FASTFOOD("Fast Food"),
    SALAD("Salad"), SANDWICH("Sandwich"), FRUIT("Fruit"), ICECREAM("Ice Cream"), CAKE("Cake");

    private String type;
    DishType(String type) {
      this.type = type;
    }

    public String getType() {
      return this.type;
    }
  }
  /*  Dietary Restriction Constants */
  enum Restrictions {
    PESCETARIAN("Pescetarian"), VEGETARIAN("Vegetarian"), VEGAN("Vegan"),
    GLUTENFREE("Gluten-Free"), DAIRYFREE("Dairy-Free"), NUTFREE("Nut-Free");

    private String type;
    Restrictions(String type) {
      this.type = type;
    }

    public String getType() {
      return this.type;
    }
  }

  /* Location Constants */
  /** fill out with location constants if time permits */
}
