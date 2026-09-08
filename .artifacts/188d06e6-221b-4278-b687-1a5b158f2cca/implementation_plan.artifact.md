# Alinhamento do Card Call of Duty (Direita)

O objetivo é corrigir o desalinhamento e as referências cruzadas no segundo card da seção "Em destaque" na `MainActivity`.

## Mudanças Propostas

### Layout [Componente: res/layout]

#### [MODIFY] [activity_main.xml](file:///Users/senai/StudioProjects/projeto_nintendo0/app/src/main/res/layout/activity_main.xml)
- Corrigir as `Constraints` (`app:layout_constraintTop_toBottomOf`) dos elementos do card da direita para que refiram-se aos elementos do próprio card (ex: `textView6` abaixo de `imageView11`, não de `imageView10`).
- Ajustar os `android:layout_marginStart` para que fiquem alinhados com o card da esquerda (mantendo 8dp de margem interna em relação à borda do card `view5`).
- Corrigir a referência de `textView8` para que fique ao lado de `view6`, não de `view4`.

## Plano de Verificação

### Verificação Manual
- Abrir o app na `MainActivity`.
- Observar a seção "Em destaque".
- O card da direita ("Call of Duty") deve estar perfeitamente alinhado verticalmente e horizontalmente com o card da esquerda.
