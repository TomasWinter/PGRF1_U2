
### Základní ovládání:
- C - smazání plátna
- V - přepnutí módu
- Prostřední tlačítko myši - vyplnění oblasti pomocí seedfill

### Vyváření úseček (LINE mode):
- Pro vytvoření úsečky stiskněte a táhněte myší
- Ovládání:
  - LMB - vytváření bodů
  - RMB - úprava pozice bodu

### Vytváření polygonů (POLYGON mode):
- Pro vytvoření polygonu stiskněte a táhněte myší
- Ovládání:
  - LMB - vytváření bodů
  - RMB - úprava pozice bodu
  - F - vypnutí/zapnutí scanline vyplnění
  - Enter - vytvoření nového polygonu
  - Delete - smazání vrcholu
    - vrchol musí být vybraný pomocí RMB

### Ořezávání čar (CROP_LINE mode):
- Postup:
  1. Nejdříve vytvořte čáry v LINE módu
  2. Přepněte se do LINE_CROP módu
  3. Nakreslete řezací obdélník (LMB)
  4. Potvrďte (Enter)
- Ovládání:
  - LMB - vytvoření ořezávacího obdélníku
  - Enter - oříznutí všech úseček

### Ořezávání polygonu (POLYGON_CROP mode):
- Postup:
  1. Přepněte se do POLYGON_CROP módu
  2. Nakreslete polygon (LMB)
  3. Potvrďte (Enter)
  4. Nakreslete ořezávací polygon (LMB)
  5. Potvrďte (Enter)
- Ovládání:
  - LMB - vytváření bodů
  - RMB - úprava pozice bodu
  - Enter - potvrzení úpravy polygonu

GITový repozitář:
https://github.com/TomasWinter/PGRF1_U2