package niv.flowstone.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import niv.flowstone.Flowstone;

public class FlowstoneConfig {

    private static FlowstoneConfig instance = null;

    private static File file = FabricLoader.getInstance().getConfigDir().resolve("flowstone.json").toFile();

    private boolean enabled;

    private List<RecipeOption> recipes;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<RecipeOption> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeOption> recipes) {
        this.recipes = recipes;
    }

    public static void save(Gson gson) {
        try (var writer = new FileWriter(file, false)) {
            writer.write(gson.toJson(getInstance()));
            writer.flush();
        } catch (IOException ex) {
            Flowstone.LOGGER.warn("Unable to save configs", ex);
        }
    }

    public static void load(Gson gson) {
        if (file.exists()) {
            try (var reader = new FileReader(file)) {
                instance = gson.fromJson(reader, FlowstoneConfig.class);
            } catch (IOException ex) {
                Flowstone.LOGGER.warn("Unable to save configs", ex);
            }
        } else {
            save(gson);
        }
    }

    public static FlowstoneConfig getInstance() {
        if (instance == null) {
            instance = getDefaultConfig();
        }
        return instance;
    }

    public static FlowstoneConfig getDefaultConfig() {
        var config = new FlowstoneConfig();
        config.enabled = true;
        config.recipes = new ArrayList<>(8);
        config.recipes.add(RecipeOption.of(Blocks.COAL_ORE, 1, 64));
        config.recipes.add(RecipeOption.of(Blocks.COPPER_ORE, 1, 64));
        config.recipes.add(RecipeOption.of(Blocks.IRON_ORE, 1, 64));
        config.recipes.add(RecipeOption.of(Blocks.GOLD_ORE, 1, 64));
        config.recipes.add(RecipeOption.of(Blocks.LAPIS_ORE, 1, 64));
        config.recipes.add(RecipeOption.of(Blocks.REDSTONE_ORE, 1, 64));
        config.recipes.add(RecipeOption.of(Blocks.EMERALD_ORE, 1, 64));
        config.recipes.add(RecipeOption.of(Blocks.DIAMOND_ORE, 1, 64));
        return config;
    }

    public static class RecipeOption {

        private Identifier block;

        private int chances;

        private int bound;

        public RecipeOption() {
            /* empty constructor */
        }

        public RecipeOption(Identifier block, int chances, int bound) {
            this.block = block;
            this.chances = chances;
            this.bound = bound;
        }

        public Identifier getBlock() {
            return block;
        }

        public void setBlock(Identifier block) {
            this.block = block;
        }

        public int getChances() {
            return chances;
        }

        public void setChances(int chances) {
            this.chances = chances;
        }

        public int getBound() {
            return bound;
        }

        public void setBound(int bound) {
            this.bound = bound;
        }

        private static RecipeOption of(Block block, int chances, int bound) {
            return new RecipeOption(Registries.BLOCK.getId(block), chances, bound);
        }
    }
}
