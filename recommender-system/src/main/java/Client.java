import java.util.logging.Logger;
import java.awt.Desktop;
import java.net.URI;

import org.camunda.bpm.client.ExternalTaskClient;

public class Client {
    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        LOGGER.info("starting client...");

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(10000) // long polling timeout
                .build();

        // subscribe to an external task topic as specified in the process
        client.subscribe("find-ingredients")
                .lockDuration(1000) // the default lock duration is 20 seconds, but you can override this
                .handler((externalTask, externalTaskService) -> {
                    // Put your business logic here
                    LOGGER.info("i am working on a task...");

                    Shampoo individualShampoo = new Shampoo();
                    //add basic ingredients
                    individualShampoo.setIngredients("distilled water, decyl glucoside, glycerine," +
                            " guar gum, coco betaine");
                    individualShampoo.setDescription("The basis for all of our shampoos is the same. Distilled water" +
                            " combined with a basic cleansing agent (decyl glucoside), thickeners such as glycerine and guar gum " +
                            "as well as coco betaine to make it a little creamier so it feels better applying it.");

                    String ingredients = individualShampoo.getIngredients();
                    String description = individualShampoo.getDescription();

                    //get the customer's age (question 1)
                    int age = externalTask.getVariable("age");
                    if(age <= 25) {
                        //add superfruits to base shampoo (moisture and shine)
                        ingredients += ", ginger, goji berries";
                        description += " For people under the age of 25 we add some superfruits for long lasting moisture and shine.";
                    } else {
                        //add ceramides, antioxidants & hyaluronic acid
                        ingredients += ", ceramides, antioxidants, hyaluronic acid";
                        description += " For people above the age of 25 we add ceramides to help repair broken fibers as well" +
                                "as reinforce the hair's structure. Additionally antioxidants and hyaluronic acid is added to" +
                                "help nourish the strands and retain moisture in the hair.";
                    }
                    LOGGER.info("age: " + age);

                    //get the customer's hair type (question 2)
                    String hairType = externalTask.getVariable("hairType");
                    if(hairType.equals("straight")) {
                        //add coconut oil, soybean, almond, sunflower oils, vitamins B5, C, and E to shampoo
                        ingredients += "coconut oil, almonds";
                        description += " Especially for our straight haired customers volume is crucial for the best look " +
                                "which is why coconut oil and almonds are added.";
                    } else if(hairType.equals("wavy") || hairType.equals("curly")) {
                        //add glycerin to shampoo
                        ingredients += ", glycerin";
                        description += " For our wavy and curly customers we add some extra glycerin to draw moisture from the air" +
                                "into the hair resulting in less frizz and more defined and shiny curls.";
                    } else { //coily
                        //add shea butter, jojoba oil, coconut oil, sunflower oil, and argan oil to shampoo
                        ingredients += ", shea butter, sunflower oil";
                        description += " For coily hair shea butter and sunflower oil is added to keep the curls and waves smooth.";
                    }
                    LOGGER.info("hairType: " + hairType);

                    //get customer's scalp moisture (question 3)
                    String scalp = externalTask.getVariable("scalp");
                    if(scalp.equals("dry")) {
                        //add Aloe Vera, glycerin, Hyaluronic Acid (conflict with question one if the person
                        // is older than 25, but shouldn’t be an issue), coconut oil, seed oils
                        ingredients += ", aloe vera";
                        description += " Aloe vera is added to help and moisturize the scalp.";
                    } else if(scalp.equals("oily")) {
                        //add lemon juice + almond oil / fresh apple cider vinegar + brewed organic green tea
                        ingredients += ", lemon juice, brewed organic green tea";
                        description += " To help make your scalp less greasy and therefore make your hair look fresh longer lemon juice and" +
                                "brewed organic green tea are added.";
                    } else { //balanced or normal
                        //add aloe vera gel, vitamin E oil, organic honey
                        //add nothing it is already balanced/normal
                    }
                    LOGGER.info("scalp: " + scalp);

                    //split ends (question 4)
                    boolean splitEnds = externalTask.getVariable("splitEnds");
                    if(splitEnds) {
                        //add plenty of argan oil, avocado oil, fish oil
                        ingredients += ", avocado oil";
                        description += " To counter split ends avocado oil is added which helps soften, repair and protect the hair.";
                    }
                    LOGGER.info("split ends: " + splitEnds);

                    //dandruff (question 5)
                    boolean dandruff = externalTask.getVariable("dandruff");
                    if(dandruff) {
                        //add onions, olive oil, apple cider vinegar
                        ingredients += ", olive oil, apple cider vinegar";
                        description += " To counter loose dry skin on the scalp olive oil and apple cider vinegar are added as well.";
                    }
                    LOGGER.info("dandruff: " + dandruff);

                    //hairLossMedium - if it is only a little more than average (question 6)
                    boolean hairLossMedium = externalTask.getVariable("hairLossMedium");
                    if(hairLossMedium) {
                        //add strained rice water (after cooking), castor oil
                        ingredients += ", castor oil";
                        description += " To strengthen the roots of your hair castor oil is added for people with a little " +
                                "more than average hair loss.";
                    }
                    LOGGER.info("hairLossMedium: " + hairLossMedium);

                    //hairLossStrong - if it runs in the family / is a genetic issue (question 7)
                    boolean hairLossStrong = externalTask.getVariable("hairLossStrong");
                    if(hairLossStrong) {
                        //add anti hair loss ingredients: phyto-caffeine, minoxidil, niacin
                        ingredients += ", phyto-caffeine, niacin";
                        description += " To help counteract genetic hair loss issues we added phyto-caffeine and niacin which is proven" +
                                " to strengthen the hair roots immensely.";
                    }
                    LOGGER.info("hairLossStrong: " + hairLossStrong);

                    //thin hair (question 8)
                    boolean thinHair = externalTask.getVariable("thinHair");
                    if(thinHair) {
                        //add coconut oil, turmeric, cider vinegar, castor oil
                        ingredients += ", coconut oil, turmeric";
                        description += " People with thin hair need more thickness and volume, which is the reason for some extra " +
                                "coconut oil and additionally turmeric.";
                    }
                    LOGGER.info("thinHair: " + thinHair);

                    //majority of diet (question 9)
                    String diet = externalTask.getVariable("diet");
                    if(diet.equals("meat") || diet.equals("fish") || diet.equals("sweets")) {
                        //add lemon and cucumber
                        ingredients += ", lemon, cucumber";
                        description += " Lemon and cucumber are added for people that have no problems with protein in their diet, " +
                                "but might lack some vitamins.";
                    }
                    if(diet.equals("vegetables") || diet.equals("sweets")) {
                        //add milk, wheat (can't add milk, it wouldn't be vegan)
                        ingredients += ", wheat";
                        description += " Wheat is added to help with protein in the hair, if it might be possible that not enough protein " +
                                "is consumed in the diet.";
                    }
                    LOGGER.info("diet: " + diet);

                    String fragrance = externalTask.getVariable("fragrance");
                    if(fragrance != null) {
                        ingredients += ", " + fragrance;
                        description += " Lastly " + fragrance + " was chosen by you and added by us as the main fragrance component.";
                    }

                    String bottleSize = externalTask.getVariable("bottleSize");
                    if(bottleSize != null) {
                        if(bottleSize.equals("S")) {
                            individualShampoo.setCost(10);
                            individualShampoo.setSize("Small");
                        } else if (bottleSize.equals("M")) {
                            individualShampoo.setCost(15);
                            individualShampoo.setSize("Medium");
                        } else {
                            individualShampoo.setCost(20);
                            individualShampoo.setSize("Large");
                        }
                    }

                    String nickName = externalTask.getVariable("nickName");
                    individualShampoo.setName(nickName + "'s dope vegan all natural individual shampoo");
                    individualShampoo.setIngredients(ingredients);
                    individualShampoo.setDescription(description);

                    LOGGER.info("Ingredients: " + individualShampoo.getIngredients());
                    LOGGER.info("Description: " + individualShampoo.getDescription());

                    LOGGER.info("Charging credit card with an amount of '" + individualShampoo.getCost()
                            + "'€ for '" + individualShampoo.getName() + "'");

                    /*
                    try {
                        Desktop.getDesktop().browse(new URI("https://docs.camunda.org/get-started/quick-start/complete"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    */
                    // Complete the task
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}
