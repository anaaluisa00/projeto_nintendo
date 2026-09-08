# Walkthrough - Alinhamento dos Cards "Em Destaque"

Corrigi o desalinhamento visual do segundo card (Call of Duty, à direita) na seção "Em destaque" da `MainActivity`.

## Mudanças Realizadas

### Layout
- **[activity_main.xml](file:///Users/senai/StudioProjects/projeto_nintendo0/app/src/main/res/layout/activity_main.xml)**:
    - Corrigi o `android:layout_marginStart` de todos os elementos do card da direita para 224dp, garantindo que fiquem alinhados com a borda interna do card (`view5`).
    - Corrigi as `Constraints` de posicionamento vertical:
        - O título (`textView6`) agora está posicionado abaixo da imagem correta (`imageView11`).
        - O botão de download (`textView7`) agora está abaixo do título (`textView6`).
        - A barrinha vermelha (`view6`) agora está abaixo do botão (`textView7`).
        - O texto do console (`textView8`) agora está alinhado ao lado da barrinha correta (`view6`).

## Verificação
As mudanças garantem que o card da direita seja um espelho exato em termos de alinhamento e estrutura do card da esquerda, eliminando as referências cruzadas que causavam o erro visual.
