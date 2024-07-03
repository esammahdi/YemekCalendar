package com.esammahdi.yemekcalendar

import com.esammahdi.yemekcalendar.core.data.room.daos.CalendarDao
import com.esammahdi.yemekcalendar.core.data.room.daos.FoodItemDao
import com.esammahdi.yemekcalendar.core.data.room.daos.InstitutionDao
import com.esammahdi.yemekcalendar.core.data.room.entities.FoodItemEntity
import com.esammahdi.yemekcalendar.core.data.room.entities.InstitutionEntity
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.ZoneId

class FirebaseWriterHelper(
    private val databaseReference: DatabaseReference,
    private val calendarDao: CalendarDao,
    private val foodItemDao: FoodItemDao,
    private val institutionDao: InstitutionDao
) {

    fun populateDatabase() {
        runBlocking {
            institutionDao.insertItems(
                listOf(
                    InstitutionEntity("Marmara"),
                    InstitutionEntity("Dorm"),
                )
            )

            foodItemDao.insertItems(foodItemsExamples)
            roomCalendarDayEntities.forEach {
                calendarDao.insertItem(it)
            }
//            calendarDao.insertItems(roomCalendarDayEntities)
        }
    }

    // Method to put a list of items under the specified child reference
    fun <T> putItems(childReference: DatabaseReference, items: List<T>) {
        for (item in items) {
            val newItemKey = childReference.push().key
            if (newItemKey != null) {
                childReference.child(newItemKey).setValue(item)
            }
        }
    }

    fun populateInstitutions() {
        val ref = databaseReference.child("institutions")
        putItems(ref, listOf("Marmara University", "IYC Dormitory"))
    }

    //
    fun populateFoodItems() {
        val ref = databaseReference.child("food_items")
        putItems(ref, foodItemsExamples)
    }

    //
    fun populateCalendartems() {
        val ref = databaseReference.child("calendarDays")

        val marmaraCalendarDays =
            generateRandomFirebaseCalendarDays("Marmara University", foodItemsExamples)
        val iycCalendarDays = generateRandomFirebaseCalendarDays("IYC Dormitory", foodItemsExamples)

        putItems(ref, marmaraCalendarDays + iycCalendarDays)

    }
}


data class CalendarDayEntity(
    val date: Long,
    val menu: List<Int>,
    val type: String
)

data class FirebaseCalendarDayEntity(
    val institution: String,
    val date: Long,
    val menu: List<Int>,
    val type: String
)

val foodItemsExamples: List<FoodItemEntity> = listOf(
    FoodItemEntity(1, "Beef Burger", 800, "MainCourse", "https://example.com/beef-burger.jpg"),
    FoodItemEntity(2, "Caesar Wrap", 450, "MainCourse", "https://example.com/caesar-wrap.jpg"),
    FoodItemEntity(3, "Tomato Bisque", 220, "Soup", "https://example.com/tomato-bisque.jpg"),
    FoodItemEntity(4, "Garlic Knots", 280, "SideDish", "https://example.com/garlic-knots.jpg"),
    FoodItemEntity(
        5,
        "Grilled Salmon",
        600,
        "MainCourse",
        "https://example.com/grilled-salmon.jpg"
    ),
    FoodItemEntity(6, "Fruit Salad", 180, "SideDish", null),
    FoodItemEntity(7, "Pumpkin Soup", 250, "Soup", "https://example.com/pumpkin-soup.jpg"),
    FoodItemEntity(
        8,
        "Margherita Pizza",
        650,
        "MainCourse",
        "https://example.com/margherita-pizza.jpg"
    ),
    FoodItemEntity(9, "Hummus", 120, "SideDish", "https://example.com/hummus.jpg"),
    FoodItemEntity(
        10,
        "Chicken Noodle Soup",
        280,
        "Soup",
        "https://example.com/chicken-noodle-soup.jpg"
    ),
    FoodItemEntity(
        11,
        "Sweet Potato Casserole",
        400,
        "SideDish",
        "https://example.com/sweet-potato-casserole.jpg"
    ),
    FoodItemEntity(12, "Shrimp Tacos", 520, "MainCourse", "https://example.com/shrimp-tacos.jpg"),
    FoodItemEntity(
        13,
        "Broccoli Cheddar Soup",
        300,
        "Soup",
        "https://example.com/broccoli-cheddar-soup.jpg"
    ),
    FoodItemEntity(14, "Caprese Salad", 220, "SideDish", "https://example.com/caprese-salad.jpg"),
    FoodItemEntity(15, "Vegetable Stir-Fry", 380, "MainCourse", null),
    FoodItemEntity(16, "Clam Chowder", 320, "Soup", "https://example.com/clam-chowder.jpg"),
    FoodItemEntity(
        17,
        "Garlic Mashed Potatoes",
        280,
        "SideDish",
        "https://example.com/garlic-mashed-potatoes.jpg"
    ),
    FoodItemEntity(18, "Tuna Salad", 350, "MainCourse", "https://example.com/tuna-salad.jpg"),
    FoodItemEntity(19, "Lentil Stew", 240, "Soup", null),
    FoodItemEntity(20, "Coleslaw", 150, "SideDish", "https://example.com/coleslaw.jpg"),
    // New entries
    FoodItemEntity(
        21,
        "BBQ Chicken Wings",
        540,
        "MainCourse",
        "https://example.com/bbq-chicken-wings.jpg"
    ),
    FoodItemEntity(22, "Minestrone Soup", 200, "Soup", "https://example.com/minestrone-soup.jpg"),
    FoodItemEntity(23, "Bruschetta", 180, "SideDish", "https://example.com/bruschetta.jpg"),
    FoodItemEntity(
        24,
        "Spaghetti Carbonara",
        620,
        "MainCourse",
        "https://example.com/spaghetti-carbonara.jpg"
    ),
    FoodItemEntity(25, "Beet Salad", 170, "SideDish", "https://example.com/beet-salad.jpg"),
    FoodItemEntity(
        26,
        "Chicken Tikka Masala",
        700,
        "MainCourse",
        "https://example.com/chicken-tikka-masala.jpg"
    ),
    FoodItemEntity(
        27,
        "French Onion Soup",
        270,
        "Soup",
        "https://example.com/french-onion-soup.jpg"
    ),
    FoodItemEntity(28, "Potato Wedges", 300, "SideDish", "https://example.com/potato-wedges.jpg"),
    FoodItemEntity(29, "Pad Thai", 640, "MainCourse", "https://example.com/pad-thai.jpg"),
    FoodItemEntity(30, "Greek Salad", 210, "SideDish", "https://example.com/greek-salad.jpg"),
    FoodItemEntity(31, "Lasagna", 750, "MainCourse", "https://example.com/lasagna.jpg"),
    FoodItemEntity(32, "Gazpacho", 190, "Soup", "https://example.com/gazpacho.jpg"),
    FoodItemEntity(
        33,
        "Mozzarella Sticks",
        340,
        "SideDish",
        "https://example.com/mozzarella-sticks.jpg"
    ),
    FoodItemEntity(34, "Fried Rice", 580, "MainCourse", "https://example.com/fried-rice.jpg"),
    FoodItemEntity(35, "Waldorf Salad", 230, "SideDish", "https://example.com/waldorf-salad.jpg"),
    FoodItemEntity(
        36,
        "Stuffed Bell Peppers",
        430,
        "MainCourse",
        "https://example.com/stuffed-bell-peppers.jpg"
    ),
    FoodItemEntity(37, "Miso Soup", 80, "Soup", "https://example.com/miso-soup.jpg"),
    FoodItemEntity(38, "French Fries", 320, "SideDish", "https://example.com/french-fries.jpg"),
    FoodItemEntity(
        39,
        "Chicken Alfredo",
        680,
        "MainCourse",
        "https://example.com/chicken-alfredo.jpg"
    ),
    FoodItemEntity(40, "Quinoa Salad", 190, "SideDish", "https://example.com/quinoa-salad.jpg"),
    FoodItemEntity(41, "Egg Drop Soup", 150, "Soup", "https://example.com/egg-drop-soup.jpg"),
    FoodItemEntity(
        42,
        "Roasted Brussels Sprouts",
        210,
        "SideDish",
        "https://example.com/roasted-brussels-sprouts.jpg"
    ),
    FoodItemEntity(
        43,
        "Beef Stroganoff",
        720,
        "MainCourse",
        "https://example.com/beef-stroganoff.jpg"
    ),
    FoodItemEntity(
        44,
        "Chicken Caesar Salad",
        310,
        "SideDish",
        "https://example.com/chicken-caesar-salad.jpg"
    ),
    FoodItemEntity(45, "Lobster Bisque", 320, "Soup", "https://example.com/lobster-bisque.jpg"),
    FoodItemEntity(
        46,
        "Stuffed Mushrooms",
        260,
        "SideDish",
        "https://example.com/stuffed-mushrooms.jpg"
    ),
    FoodItemEntity(
        47,
        "Butter Chicken",
        610,
        "MainCourse",
        "https://example.com/butter-chicken.jpg"
    ),
    FoodItemEntity(48, "Cobb Salad", 290, "SideDish", "https://example.com/cobb-salad.jpg"),
    FoodItemEntity(
        49,
        "Manhattan Clam Chowder",
        300,
        "Soup",
        "https://example.com/manhattan-clam-chowder.jpg"
    ),
    FoodItemEntity(50, "Garlic Bread", 240, "SideDish", "https://example.com/garlic-bread.jpg"),
    FoodItemEntity(
        51,
        "Chicken Quesadilla",
        560,
        "MainCourse",
        "https://example.com/chicken-quesadilla.jpg"
    ),
    FoodItemEntity(
        52,
        "Seafood Paella",
        670,
        "MainCourse",
        "https://example.com/seafood-paella.jpg"
    ),
    FoodItemEntity(53, "Ratatouille", 230, "MainCourse", "https://example.com/ratatouille.jpg"),
    FoodItemEntity(54, "Tortilla Soup", 220, "Soup", "https://example.com/tortilla-soup.jpg"),
    FoodItemEntity(55, "Macaroni Salad", 220, "SideDish", "https://example.com/macaroni-salad.jpg"),
    FoodItemEntity(
        56,
        "Chicken Parmesan",
        640,
        "MainCourse",
        "https://example.com/chicken-parmesan.jpg"
    ),
    FoodItemEntity(57, "Lentil Soup", 190, "Soup", "https://example.com/lentil-soup.jpg"),
    FoodItemEntity(58, "Onion Rings", 290, "SideDish", "https://example.com/onion-rings.jpg"),
    FoodItemEntity(59, "Beef Tacos", 470, "MainCourse", "https://example.com/beef-tacos.jpg"),
    FoodItemEntity(60, "Avocado Toast", 300, "SideDish", "https://example.com/avocado-toast.jpg"),
    FoodItemEntity(61, "Borscht", 150, "Soup", "https://example.com/borscht.jpg"),
    FoodItemEntity(
        62,
        "Stuffed Zucchini",
        220,
        "SideDish",
        "https://example.com/stuffed-zucchini.jpg"
    ),
    FoodItemEntity(63, "Falafel", 330, "MainCourse", "https://example.com/falafel.jpg"),
    FoodItemEntity(
        64,
        "Pasta Primavera",
        550,
        "MainCourse",
        "https://example.com/pasta-primavera.jpg"
    ),
    FoodItemEntity(
        65,
        "Thai Green Curry",
        600,
        "MainCourse",
        "https://example.com/thai-green-curry.jpg"
    ),
    FoodItemEntity(
        66,
        "Mulligatawny Soup",
        180,
        "Soup",
        "https://example.com/mulligatawny-soup.jpg"
    ),
    FoodItemEntity(
        67,
        "Cauliflower Rice",
        140,
        "SideDish",
        "https://example.com/cauliflower-rice.jpg"
    ),
    FoodItemEntity(
        68,
        "Turkey Sandwich",
        450,
        "MainCourse",
        "https://example.com/turkey-sandwich.jpg"
    ),
    FoodItemEntity(69, "Gazpacho", 110, "Soup", "https://example.com/gazpacho.jpg"),
    FoodItemEntity(70, "Poutine", 510, "SideDish", "https://example.com/poutine.jpg"),
    FoodItemEntity(
        71,
        "Vegetable Curry",
        520,
        "MainCourse",
        "https://example.com/vegetable-curry.jpg"
    ),
    FoodItemEntity(72, "Crab Cakes", 340, "MainCourse", "https://example.com/crab-cakes.jpg"),
    FoodItemEntity(73, "Avgolemono Soup", 170, "Soup", "https://example.com/avgolemono-soup.jpg"),
    FoodItemEntity(
        74,
        "Green Bean Almondine",
        200,
        "SideDish",
        "https://example.com/green-bean-almondine.jpg"
    ),
    FoodItemEntity(
        75,
        "Beef Wellington",
        790,
        "MainCourse",
        "https://example.com/beef-wellington.jpg"
    ),
    FoodItemEntity(76, "Tabbouleh", 220, "SideDish", "https://example.com/tabbouleh.jpg"),
    FoodItemEntity(77, "Bouillabaisse", 310, "Soup", "https://example.com/bouillabaisse.jpg"),
    FoodItemEntity(78, "Samosas", 330, "SideDish", "https://example.com/samosas.jpg"),
    FoodItemEntity(79, "Jambalaya", 690, "MainCourse", "https://example.com/jambalaya.jpg"),
    FoodItemEntity(80, "Ramen", 430, "MainCourse", "https://example.com/ramen.jpg"),
    FoodItemEntity(81, "Corn Chowder", 250, "Soup", "https://example.com/corn-chowder.jpg"),
    FoodItemEntity(
        82,
        "Eggplant Parmesan",
        410,
        "SideDish",
        "https://example.com/eggplant-parmesan.jpg"
    ),
    FoodItemEntity(
        83,
        "Chicken Pot Pie",
        600,
        "MainCourse",
        "https://example.com/chicken-pot-pie.jpg"
    ),
    FoodItemEntity(
        84,
        "Spinach Artichoke Dip",
        270,
        "SideDish",
        "https://example.com/spinach-artichoke-dip.jpg"
    ),
    FoodItemEntity(
        85,
        "Hot and Sour Soup",
        190,
        "Soup",
        "https://example.com/hot-and-sour-soup.jpg"
    ),
    FoodItemEntity(
        86,
        "Curried Lentils",
        310,
        "MainCourse",
        "https://example.com/curried-lentils.jpg"
    ),
    FoodItemEntity(
        87,
        "Chicken Shawarma",
        530,
        "MainCourse",
        "https://example.com/chicken-shawarma.jpg"
    ),
    FoodItemEntity(88, "Pea Soup", 180, "Soup", "https://example.com/pea-soup.jpg"),
    FoodItemEntity(
        89,
        "Zucchini Fritters",
        200,
        "SideDish",
        "https://example.com/zucchini-fritters.jpg"
    ),
    FoodItemEntity(
        90,
        "Chili Con Carne",
        490,
        "MainCourse",
        "https://example.com/chili-con-carne.jpg"
    ),
    FoodItemEntity(
        91,
        "Shepherd's Pie",
        670,
        "MainCourse",
        "https://example.com/shepherds-pie.jpg"
    ),
    FoodItemEntity(
        92,
        "Mushroom Risotto",
        510,
        "MainCourse",
        "https://example.com/mushroom-risotto.jpg"
    ),
    FoodItemEntity(93, "Gumbo", 480, "MainCourse", "https://example.com/gumbo.jpg"),
    FoodItemEntity(
        94,
        "Mulligatawny Soup",
        210,
        "Soup",
        "https://example.com/mulligatawny-soup.jpg"
    ),
    FoodItemEntity(95, "Kale Chips", 90, "SideDish", "https://example.com/kale-chips.jpg"),
    FoodItemEntity(96, "Shrimp Scampi", 600, "MainCourse", "https://example.com/shrimp-scampi.jpg"),
    FoodItemEntity(97, "Goulash", 550, "MainCourse", "https://example.com/goulash.jpg"),
    FoodItemEntity(98, "Pho", 380, "MainCourse", "https://example.com/pho.jpg"),
    FoodItemEntity(99, "Minestrone", 200, "Soup", "https://example.com/minestrone.jpg"),
    FoodItemEntity(100, "Potato Gratin", 320, "SideDish", "https://example.com/potato-gratin.jpg"),
    FoodItemEntity(101, "Pancakes", 320, "MainCourse", "https://example.com/pancakes.jpg"),
    FoodItemEntity(102, "Waffles", 350, "MainCourse", "https://example.com/waffles.jpg"),
    FoodItemEntity(103, "Omelette", 270, "MainCourse", "https://example.com/omelette.jpg"),
    FoodItemEntity(104, "French Toast", 300, "MainCourse", "https://example.com/french-toast.jpg"),
    FoodItemEntity(
        105,
        "Bacon and Eggs",
        450,
        "MainCourse",
        "https://example.com/bacon-and-eggs.jpg"
    ),
    FoodItemEntity(106, "Hash Browns", 250, "SideDish", "https://example.com/hash-browns.jpg"),
    FoodItemEntity(
        107,
        "Bagel with Cream Cheese",
        340,
        "SideDish",
        "https://example.com/bagel-with-cream-cheese.jpg"
    ),
    FoodItemEntity(108, "Croissant", 230, "SideDish", "https://example.com/croissant.jpg"),
    FoodItemEntity(
        109,
        "Breakfast Burrito",
        600,
        "MainCourse",
        "https://example.com/breakfast-burrito.jpg"
    ),
    FoodItemEntity(
        110,
        "Smoothie Bowl",
        220,
        "MainCourse",
        "https://example.com/smoothie-bowl.jpg"
    ),
    FoodItemEntity(111, "Avocado Toast", 300, "SideDish", "https://example.com/avocado-toast.jpg"),
    FoodItemEntity(112, "Granola", 150, "SideDish", "https://example.com/granola.jpg"),
    FoodItemEntity(113, "Chia Pudding", 180, "SideDish", "https://example.com/chia-pudding.jpg"),
    FoodItemEntity(114, "Frittata", 280, "MainCourse", "https://example.com/frittata.jpg"),
    FoodItemEntity(
        115,
        "Eggs Benedict",
        450,
        "MainCourse",
        "https://example.com/eggs-benedict.jpg"
    ),
    FoodItemEntity(116, "Shakshuka", 320, "MainCourse", "https://example.com/shakshuka.jpg"),
    FoodItemEntity(117, "Quiche", 400, "MainCourse", "https://example.com/quiche.jpg"),
    FoodItemEntity(118, "Muffins", 220, "SideDish", "https://example.com/muffins.jpg"),
    FoodItemEntity(
        119,
        "Cinnamon Rolls",
        350,
        "SideDish",
        "https://example.com/cinnamon-rolls.jpg"
    ),
    FoodItemEntity(
        120,
        "Breakfast Sandwich",
        500,
        "MainCourse",
        "https://example.com/breakfast-sandwich.jpg"
    )
)


fun generateRandomCalendarDays(foodItems: List<FoodItemEntity>): List<CalendarDayEntity> {
    val foodItemsByType = foodItems.groupBy { it.foodType }
    val allDates = generateAllUniqueDatesForYear(2024, 360)

    return allDates.map { date ->
        val mainCourse = foodItemsByType["MainCourse"]!!.random().id
        val soup = foodItemsByType["Soup"]!!.random().id
        val sideDish = foodItemsByType["SideDish"]!!.random().id
        val menu = listOf(mainCourse, soup, sideDish, foodItems.random().id)

        val dayOfWeek = LocalDate.ofEpochDay(date).dayOfWeek
        val type =
            if (dayOfWeek == java.time.DayOfWeek.SATURDAY || dayOfWeek == java.time.DayOfWeek.SUNDAY) "Weekend" else "Normal"

        CalendarDayEntity(date, menu, type)
    }
}

fun generateRandomFirebaseCalendarDays(
    insitution: String,
    foodItems: List<FoodItemEntity>
): List<FirebaseCalendarDayEntity> {
    val foodItemsByType = foodItems.groupBy { it.foodType }
    val allDates = generateAllUniqueDatesForYear(2024, 300)

    return allDates.map { date ->
        val mainCourse = foodItemsByType["MainCourse"]!!.random().id
        val soup = foodItemsByType["Soup"]!!.random().id
        val sideDish = foodItemsByType["SideDish"]!!.random().id
        val menu = listOf(mainCourse, soup, sideDish, foodItems.random().id)

        val dayOfWeek = LocalDate.ofEpochDay(date).dayOfWeek
        val type =
            if (dayOfWeek == java.time.DayOfWeek.SATURDAY || dayOfWeek == java.time.DayOfWeek.SUNDAY) "Weekend" else "Normal"

        FirebaseCalendarDayEntity(insitution, date, menu, type)
    }
}

fun generateAllUniqueDatesForYear(year: Int, count: Int): List<Long> {
    val dates = mutableListOf<LocalDate>()
    val startDate = LocalDate.of(year, 1, 1)
    val endDate = LocalDate.of(year, 12, 31)

    while (dates.size < count) {
        val randomDay = startDate.plusDays(
            (0 until endDate.toEpochDay() - startDate.toEpochDay()).random().toLong()
        )
        if (!dates.contains(randomDay)) {
            dates.add(randomDay)
        }
    }

    return dates.map { it.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() }
}


val roomCalendarDayEntities: List<com.esammahdi.yemekcalendar.core.data.room.entities.CalendarDayEntity> =
    generateRandomCalendarDays(foodItemsExamples).map { calendarDay ->
        toRoomCalendarDayEntity(calendarDay)
    }

fun toRoomCalendarDayEntity(calendarDay: CalendarDayEntity): com.esammahdi.yemekcalendar.core.data.room.entities.CalendarDayEntity {
    val menu = calendarDay.menu.map { foodItemId ->
        foodItemsExamples.find { it.id == foodItemId }
            ?: FoodItemEntity(
                7,
                "Pumpkin Soup",
                250,
                "Soup",
                "https://example.com/pumpkin-soup.jpg"
            )
    }

    return com.esammahdi.yemekcalendar.core.data.room.entities.CalendarDayEntity(
        institution = "Marmara",
        date = calendarDay.date,
        menu = menu,
        type = calendarDay.type
    )
}



