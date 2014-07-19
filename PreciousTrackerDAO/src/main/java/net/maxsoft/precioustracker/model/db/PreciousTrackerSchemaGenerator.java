package net.maxsoft.precioustracker.model.db;
/**
 * 
 */


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Used for generating DAOs with greenDAO
 * 
 * @author Max
 *
 */
public class PreciousTrackerSchemaGenerator {

    private static final String PACKAGE_NAME_DEFAULT = "net.maxsoft.precioustracker.model.dao";
    private static final String PACKAGE_NAME_DAO = "net.maxsoft.precioustracker.model.dao";
    private static final String PACKAGE_NAME_TEST = "net.maxsoft.precioustracker.test.dao";
    private static final String OUTPUT_DIR = "src/main/java";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, PACKAGE_NAME_DEFAULT);
        schema.setDefaultJavaPackageDao(PACKAGE_NAME_DAO);
        schema.setDefaultJavaPackageTest(PACKAGE_NAME_TEST);
        schema.enableKeepSectionsByDefault();

        Entity category = getCategoryEntity(schema);
        Entity item = getItemEntity(schema, category);
        createMoveEntity(schema, item);

        DaoGenerator gen = new DaoGenerator();
        gen.generateAll(schema, OUTPUT_DIR);
    }

    private static Entity getCategoryEntity(Schema schema) {
        Entity category = schema.addEntity(PreciousCategoryMeta.TABLE_NAME);
        category.addIdProperty();
        category.addStringProperty(PreciousCategoryMeta.NAME).notNull();
        
        return category;
    }

    private static Entity getItemEntity(Schema schema, Entity category) {
        Entity item = schema.addEntity(PreciousItemMeta.TABLE_NAME);
        item.addIdProperty();
        item.addStringProperty(PreciousItemMeta.NAME).notNull();
        item.addStringProperty(PreciousItemMeta.LOCATION);
        item.addDateProperty(PreciousItemMeta.DATE_CREATED);
        item.addDateProperty(PreciousItemMeta.LAST_MOVED);
        item.addStringProperty(PreciousItemMeta.PHOTO_FILE_PATH);
        // adding relation to category
        Property catProp = item.addLongProperty(PreciousItemMeta.CATEGORY).getProperty();
        item.addToOne(category, catProp);
        // adding relation to item on category
        category.addToMany(item, catProp);

        return item;
    }

    private static void createMoveEntity(Schema schema, Entity item) {
        Entity move = schema.addEntity(PreciousMoveMeta.TABLE_NAME);
        move.addIdProperty();
        move.addStringProperty(PreciousMoveMeta.FROM_WHERE).notNull();
        move.addStringProperty(PreciousMoveMeta.TO_WHERE).notNull();
        move.addDateProperty(PreciousMoveMeta.DATE_MOVED).notNull();
        move.addStringProperty(PreciousMoveMeta.SNAPSHOT_FILE_PATH);
        // adding relation to item
        Property itemId = move.addLongProperty(PreciousMoveMeta.ITEM).notNull()
                .getProperty();
        move.addToOne(item, itemId);
        // adding relation to move on item
        item.addToMany(move, itemId);
    }

}
