# Ester Sabino — protótipo

App Android de apresentação das ações extensionistas em saúde da **Universidade
Federal do Tocantins**, com convite em realidade aumentada.

O planejamento completo está em [`planejamento.md`](planejamento.md).

---

## Estado atual

Protótipo com **conteúdo genérico** e a **realidade aumentada implementada**.
Nenhuma tela tem texto fixo no código: tudo vem de
[`Conteudo.kt`](app/src/main/java/br/edu/uft/estersabino/data/Conteudo.kt).

### ✅ Compila e empacota

```
BUILD SUCCESSFUL     · debug e release
Avisos do Kotlin     · nenhum
Android Lint         · sem erros
```

| Artefato | Tamanho |
|---|---|
| `app-debug.apk` | 57,5 MB (universal, instalável já — assinado com a chave de debug) |
| `app-release-unsigned.apk` | 35,8 MB (só ARM; precisa ser assinado) |

Verificado no APK gerado: o `model_greeting.glb` sai **byte a byte idêntico** ao
original, o `FileProvider` resolve para `br.edu.uft.estersabino.fileprovider` e o
ARCore está declarado como `optional` — o app instala em aparelho sem suporte.

> ⚠️ **O que ainda NÃO foi verificado: o comportamento em tela.** Compilar prova
> que o código está correto, não que o avatar aparece do tamanho certo, virado
> para o lado certo. Isso só um celular físico responde — veja o roteiro de
> teste abaixo.

---

## Como abrir e rodar

### 1. Pré-requisitos

- **Android Studio** (versão recente — o projeto usa AGP 8.13.2)
- **JDK 17** (o Android Studio já traz um embutido)
- Um **celular Android físico**. A RA **não funciona em emulador**: não há câmera
  nem ARCore de verdade.

### 2. Abrir

```
Android Studio → Open → selecione a pasta /home/bruno/code/paty
```

Na primeira abertura o Gradle vai baixar as dependências (~5 min).

Se ele reclamar do SDK, aceite instalar a **Android SDK Platform 36**.

### 3. Rodar no celular

1. No celular: **Configurações → Sobre o telefone** → toque 7× em "Número da
   compilação" para liberar as opções de desenvolvedor;
2. **Opções do desenvolvedor → Depuração USB**: ligar;
3. Conecte por USB, autorize o computador;
4. No Android Studio, selecione o aparelho e clique em **Run ▶**.

### 4. Instalar direto o APK de teste

Já existe um APK instalável, assinado com a chave de debug:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Serve para testar tudo agora. **Não use este no evento** — para distribuição,
gere o release assinado.

### 5. Gerar o APK de distribuição

Primeiro crie um keystore (*Build → Generate Signed App Bundle / APK*), depois
coloque os dados em `~/.gradle/gradle.properties` — **nunca neste repositório**:

```properties
ESTER_STORE_FILE=/caminho/para/ester-sabino.jks
ESTER_STORE_PASSWORD=...
ESTER_KEY_ALIAS=...
ESTER_KEY_PASSWORD=...
```

Depois:

```bash
./gradlew assembleRelease
```

Sai assinado em `app/build/outputs/apk/release/`. Sem essas propriedades o build
funciona igual, só produz um APK **sem assinatura**, que o Android não instala.

> 🔑 **GUARDE O KEYSTORE.** Sem ele não é possível publicar uma atualização como
> o mesmo app: os visitantes teriam que desinstalar e reinstalar.

### Por que o release é menor

O APK de distribuição inclui só as bibliotecas nativas **ARM**
(`arm64-v8a` + `armeabi-v7a`), que cobrem praticamente todo celular Android. As
versões x86/x86_64 do Filament e do ARCore pesam ~14 MB e só servem a emuladores,
onde a RA não funciona de qualquer forma. O build de **debug segue universal**,
para quem quiser abrir as telas num emulador. Para reverter, veja `abiFilters`
em `app/build.gradle.kts`.

---

## Estrutura

```
app/src/main/
├── assets/model_greeting.glb        avatar com a animação "Greeting"
├── java/br/edu/uft/estersabino/
│   ├── MainActivity.kt
│   ├── data/Conteudo.kt             ← TODO O TEXTO DO APP ESTÁ AQUI
│   └── ui/
│       ├── theme/                   paleta e tipografia da UFT
│       ├── nav/Navegacao.kt         abas e rotas
│       ├── comum/Componentes.kt     blocos reutilizados
│       ├── telas/                   Início, Projetos, Evento, Saiba Mais
│       └── ar/                      o convite em realidade aumentada
└── res/                             ícone, cores, tema da janela
```

### O módulo de RA

| Arquivo | Papel |
|---|---|
| `ConviteArTela.kt` | Tela cheia: permissão, escolha do modo, interface |
| `ArDisponibilidade.kt` | Detecta se o aparelho tem ARCore |
| `CenaAvatar.kt` | Constantes da cena e o cálculo da posição do avatar |
| `CameraComAvatar.kt` | Modo simplificado (CameraX + cena 3D transparente) |
| `TextoSubindo.kt` | "Venha nos conhecer!!" subindo em loop |
| `Captura.kt` | Foto da tela e compartilhamento |

**Sem QR Code e sem detecção de superfície.** O convite abre por botão, e a
âncora nasce no ar a 2 m à frente da câmera, calculada a partir da pose dela no
primeiro quadro rastreado. Por isso o avatar aparece imediatamente, sem pedir
que o visitante varra o chão com o celular.

**Dois modos, escolhidos sozinho:**

- **Com ARCore** — avatar plantado no ambiente; dá para andar em volta dele.
- **Sem ARCore** — câmera + avatar por cima, preso à tela. Mesma animação, mesmo
  texto. O visitante não vê nenhuma mensagem de erro.

---

## Roteiro de teste no celular

Ordem sugerida — as três primeiras validam o app, as demais o convite:

1. **Abre e navega** pelas 4 abas sem travar
2. **Aba Projetos**: as 3 abas superiores trocam de projeto
3. **Modo escuro** (Configurações do Android): as telas continuam legíveis
4. **Botão "Ver o convite em RA"**: pede permissão de câmera na 1ª vez
5. **O avatar aparece** em alguns segundos
6. ⚠️ **O avatar está de frente?** Se aparecer de costas, veja abaixo
7. **O tamanho está certo?** Deve parecer uma pessoa a ~2 m
8. **O texto sobe em loop**, sem engasgo entre as repetições
9. **Andando com o celular**: o avatar fica onde estava (só no modo com ARCore)
10. **Botão da foto**: abre o menu de compartilhar com a imagem correta
11. **Fechar (✕)**: volta para a aba EVENTO
12. Repetir em **um segundo aparelho**, de preferência sem ARCore

---

## Versões fixadas (não baixe nenhuma delas)

| Componente | Versão | Por quê |
|---|---|---|
| Kotlin | 2.4.10 | O SceneView 4.28.0 é compilado com ela; versão menor dá erro de metadata |
| Compose BOM | 2026.06.01 | Exigida pelo SceneView |
| AGP / Gradle | 8.13.2 / 8.14.5 | 8.14.5 evita o aviso de depreciação do plugin Kotlin |
| compileSdk / target | 36 | Instale a SDK Platform 36 pelo SDK Manager |
| SceneView | 4.28.0 | Traz ARCore 1.54.0 e o Filament juntos |

## Sobre as permissões

O app pede **CAMERA** (única com prompt em tela) e herda **INTERNET** e
**VIBRATE** declaradas pela biblioteca SceneView. O código do app **não faz
nenhuma chamada de rede** — todo o conteúdo está empacotado no APK, então ele
funciona mesmo se o Wi-Fi do campus cair.

## Ajustes que provavelmente você vai querer fazer

Todos em constantes de um arquivo só, sem caçar números soltos:

**Avatar de costas** → `CenaAvatar.kt`, troque `AJUSTE_GIRO_GRAUS` de `0f` para
`180f`. Não deu para determinar isso pelo arquivo: o glTF não define um "para
frente", e este modelo veio do exportador do Three.js. Alternativa sem
recompilar: **dois dedos giram o avatar** na tela.

**Avatar muito longe / muito perto** → `DISTANCIA_METROS` (padrão `2.0f`)

**Avatar flutuando ou enterrado** → `ALTURA_CELULAR_METROS` (padrão `1.45f`)

**Avatar grande ou pequeno demais** → `ALTURA_METROS` (padrão `1.75f`)

**Texto subindo rápido ou devagar** → `TextoSubindo.kt`, parâmetro `duracaoMs`

**Trocar qual projeto é o do evento** → `Conteudo.kt`, campo `evento.projetoId`

> O botão da foto não aparece em Android 7 (API 24–25): a captura usa
> `PixelCopy` sobre a janela, que só existe a partir do Android 8. O convite
> funciona normalmente nesses aparelhos, só sem a captura.

---

## Sobre o avatar

`model_greeting.glb`, 5,98 MB — inspecionado e validado:

- Animação **"Greeting"**, 4,83 s, já emenda em loop (diferença 1º↔último
  keyframe = 0.0000)
- **In place**: o quadril desloca 4 mm na horizontal, o avatar não sai do lugar
- 66 joints, 35.450 triângulos, altura 1,897 m
- Textura PNG 2048×2048 (3,9 MB)

**O material é totalmente emissivo** (`baseColor` preto, textura ligada ao canal
de emissão): o avatar se auto-ilumina e ignora a luz da cena. Isso foi mantido de
propósito — dá o visual de holograma que combina com o convite futurista, e
garante que ele não some num ambiente mal iluminado. Para converter para PBR
normal, basta reescrever o JSON interno do `.glb` movendo a textura de `emissive`
para `baseColor`.

---

## O que ainda falta

- Substituir o conteúdo genérico pelos dados reais dos 3 projetos e do evento
- Fotos dos projetos e retrato de Ester Sabino (**com licença de uso**)
- Logo da UFT em alta resolução (a atual tem 348 px)
- Configurar a assinatura do APK e guardar o keystore
