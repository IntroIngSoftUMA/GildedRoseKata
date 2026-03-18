package org.iis.gildedrose.refactored;

class GildedRose {
  public static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
  public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
  public static final String AGED_BRIE = "Aged Brie";
  Item[] items;

  public GildedRose(Item[] items) {
    this.items = items;
  }

  public void updateQuality() {
    for (Item item : items) {
      updateItem(item);
    }
  }

  private void updateItem(Item item) {
    updateQualityAtTheEndOfTheDay(item);
    decreaseSellIn(item);
    updateQualityIfExpired(item);
  }

  private void updateQualityAtTheEndOfTheDay(Item item) {
    if (!isAgedBrie(item) && !isBackstagePass(item)) {
      decreaseQuality(item);
    } else {
      increaseQuality(item);

      if (isBackstagePass(item)) {
        if (item.sellIn < 11) {
          increaseQuality(item);
        }

        if (item.sellIn < 6) {
          increaseQuality(item);
        }
      }
    }
  }

  private void updateQualityIfExpired(Item item) {
    if (hasExpired(item)) {
      if (!isAgedBrie(item)) {
        if (!isBackstagePass(item)) {
          decreaseQuality(item);
        } else {
          item.quality = 0;
        }
      } else {
        increaseQuality(item);
      }
    }
  }

  private void decreaseSellIn(Item item) {
    if (!isSulfuras(item)) {
      item.sellIn = item.sellIn - 1;
    }
  }

  private static boolean isSulfuras(Item item) {
    return item.name.equals(SULFURAS);
  }

  private static boolean isBackstagePass(Item item) {
    return item.name.equals(BACKSTAGE_PASS);
  }

  private boolean isAgedBrie(Item item) {
    return item.name.equals(AGED_BRIE);
  }

  private void decreaseQuality(Item item) {
    if (item.quality > 0) {
      if (!isSulfuras(item)) {
        item.quality = item.quality - 1;
      }
    }
  }

  private void increaseQuality(Item item) {
    if (item.quality < 50) {
      item.quality = item.quality + 1;
    }
  }

  private boolean hasExpired(Item item) {
    return item.sellIn < 0;
  }
}
