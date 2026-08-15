# Planejamento — App "Ester Sabino"

**Plataforma de Ações Extensionistas em Saúde — Universidade Federal do Tocantins (UFT)**

> Documento de planejamento. **Nenhum código será escrito nesta fase.**
> Versão: 0.4 — 11/08/2026
> *v0.2: nome definido; QR Code removido; RA acionada por botão.*
> *v0.3: confirmado que o avatar não fica preso a superfície — detecção de plano removida do escopo.*
> *v0.4: **avatar recebido e validado** (`model_greeting.glb`); F4 desbloqueada; resta só o conteúdo.*
> *v0.5: **todas as decisões fechadas** — conteúdo genérico, evento na UFT, distribuição por APK direto. Planejamento concluído.*
> *v0.6: **protótipo F0→F7 escrito**. Ver `README.md` para build e teste.*
> *v0.7: **build verificado** — compila em debug e release, lint sem erros, APK instalável gerado.*

---

## 1. Visão geral

Aplicativo **Android** chamado **Ester Sabino**, para apresentar 3 projetos de
extensão da área da saúde da UFT, com:

- Navegação por abas entre os 3 projetos;
- Uma área de **destaque ("EVENTO")** com detalhamento aprofundado de um deles e
  um **convite em Realidade Aumentada acionado por botão**;
- Uma área **"Saiba Mais"** explicando o nome da plataforma e homenageando Ester Sabino.

**Objetivo do MVP:** app funcional, instalável em celular Android, apresentável no
evento presencial na universidade. Conteúdo textual pode ser genérico/placeholder
na versão preliminar.

---

## 2. Resposta direta: "o resto você consegue se virar?"

**Sim.** Com o avatar em `.glb` do seu lado, eu faço todo o resto sozinho:
projeto Android completo, telas, identidade visual, câmera, cena 3D, animação do
avatar, texto subindo em loop, fallback para celulares sem AR, ícone, splash e o
APK final.

**Mas há uma condição no formato do arquivo que muda bastante o trabalho.** Leia
a seção 3 antes de exportar qualquer coisa — exportar errado custa retrabalho.

### O que eu faço

| Item | Situação |
|---|---|
| Projeto Android completo (Kotlin + Compose + Gradle) | ✅ |
| Navegação, abas, telas, tema e identidade visual UFT | ✅ |
| Permissão e preview de câmera (CameraX) | ✅ |
| Cena 3D/AR, iluminação, sombra, escala e posicionamento do avatar | ✅ |
| Carregar `.glb` e reproduzir a animação em loop | ✅ |
| Texto "Venha nos conhecer!!" subindo em loop | ✅ |
| Fallback para celulares sem ARCore | ✅ |
| Captura de foto e compartilhamento | ✅ |
| Todo o conteúdo textual placeholder | ✅ |
| Ícone, splash, APK assinado, guia de instalação | ✅ |

### O que continua sendo seu

| Item | Por quê |
|---|---|
| Gerar o `.glb` do avatar **com a animação embutida** | Rig e animação são autoria em software 3D (GUI), não código |
| Testar a RA em celular físico | Emulador não tem câmera nem ARCore reais |
| Compilar e instalar o APK | Sem acesso ao seu dispositivo — eu entrego o projeto + passo a passo |

---

## 3. ✅ Avatar — RECEBIDO E VALIDADO

**Arquivo:** `model_greeting.glb` — 5,98 MB. Inspecionado e aprovado.

| Verificação | Resultado |
|---|---|
| Animação embutida | ✅ **1 animação, `"Greeting"`, 4,83 s, 67 canais** |
| Esqueleto (skin) | ✅ 66 joints — nomenclatura Unreal (`root`, `pelvis`, `spine_01`…) |
| **"In Place"** | ✅ Deslocamento horizontal do pelvis = **4 mm**. Não sai do lugar |
| **Loop** | ✅ Diferença 1º↔último keyframe = **0.0000**. Emenda perfeita, sem solavanco |
| Altura | ✅ 1,897 unidades ≈ **1,90 m** — escala correta (1 unidade = 1 m) |
| Triângulos | ✅ **35.450** — dentro do orçamento de celular (limite 50k) |
| Textura | ⚠️ PNG **2048×2048**, 3,9 MB — no limite, mas aceitável |
| Orientação | ✅ Y para cima |

**Conclusão: o avatar está pronto para uso. A Fase F4 está desbloqueada.**
Nada de Blender, nada de Mixamo, nada de retargeting. Não preciso de mais
nenhum arquivo de avatar.

### 3.1 ⚠️ Uma observação sobre o material (decisão sua)

O material do modelo está configurado como **totalmente emissivo (unlit)**:

```
baseColorFactor: [0, 0, 0, 1]   ← preto
emissiveFactor:  [1, 1, 1]      ← textura ligada ao canal de emissão
```

Na prática: **o avatar se auto-ilumina e ignora completamente a luz da cena.**
Isso tem dois lados:

| | Manter emissivo (como está) | Converter para PBR normal |
|---|---|---|
| Aparência | Achatado, brilho próprio, ar de **holograma/projeção** | Volume e sombreado reais, parece pessoa no ambiente |
| Luz do ambiente (ARCore *Light Estimation*) | Ignorada | Aproveitada — casa com a luz real |
| Em ambiente escuro | Continua visível e legível | Pode ficar escuro demais |
| Combina com "convite futurista"? | **Sim, bastante** | Menos |

**Recomendação: manter como está.** O visual de holograma serve ao efeito
futurista que você quer, e tem a vantagem prática de o avatar nunca sumir num
ambiente mal iluminado — o que num evento é bem provável.

Se preferir o visual realista, **eu faço a conversão por código** (é só reescrever
o JSON interno do `.glb`: mover a textura de `emissive` para `baseColor`). Não
precisa de você nem de software 3D. Dá para gerar as duas versões e comparar no
celular antes de decidir.

*Observações menores, que eu resolvo sozinho:* o modelo tem atributo `TANGENT` sem
usar normal map (peso morto — dá para remover e reduzir o arquivo), está
`doubleSided` (custo extra de renderização), e a roupa é roxa/vinho, que contrasta
com a paleta verde da UFT — nada impeditivo, só vale saber na hora de compor a tela.

---

## 4. Módulo de Realidade Aumentada (novo desenho, sem QR Code)

### 4.1 Fluxo do usuário — simplificado

```
Aba EVENTO
   │
   └─► [ ✨ Ver o convite em RA ]        ← botão grande, destaque amarelo
          │
          ├─► Solicita permissão de CÂMERA (só na 1ª vez)
          │
          ├─► Abre a câmera em tela cheia
          │
          └─► CENA EM LOOP INFINITO:
                 • Avatar aparece com fade-in suave
                 • Animação "vem cá" tocando em loop
                 • "Venha nos conhecer!!" sobe da base ao topo,
                   some com fade, reinicia embaixo
                 • Botões: [✕ Fechar]  [📷 Foto]  [↗ Compartilhar]
```

Sem leitura de código, sem alvo, sem etapa intermediária. **Um toque e o convite
aparece** — que é exatamente o efeito de "convite futurista" que você descreveu.

### 4.2 Decisão técnica: **sem detecção de superfície**

**Confirmado:** o avatar aparece sobre a câmera e **não fica preso a superfície,
chão ou arte impressa**. Isso elimina a *plane detection* do escopo — uma
simplificação relevante:

- ❌ Sai a tela de "mova o celular devagar para detectar o chão"
- ❌ Sai a espera até o ARCore achar uma superfície
- ❌ Sai a dependência de piso texturizado e bem iluminado
- ✅ **O avatar aparece instantaneamente ao tocar o botão** — que é o efeito de convite que você quer

Restam duas formas de posicionar o avatar no ar:

| | **A — Câmera pura** (CameraX + Filament) | **B — ARCore sem plano** |
|---|---|---|
| Posição | Fixa na tela | Fixa **no espaço**, a ~2 m à frente |
| Ao mover o celular | Avatar acompanha a tela | Avatar fica onde estava; dá para andar em volta |
| Sensação | Adesivo sobre a câmera | Presença real no ambiente |
| Compatibilidade | **Todo Android** | Só aparelhos com ARCore |
| Precisa de superfície | Não | **Não** — âncora criada no ar |

**Recomendação: B como principal, A como fallback automático.**

O app testa `ArCoreApk.checkAvailability()` ao abrir a tela:
- **Tem ARCore** → cria uma âncora no ar a ~2 m à frente da câmera, no instante em
  que a cena abre. Sem plano, sem espera. O avatar fica plantado ali e a pessoa
  pode circular em volta — é o que dá o efeito futurista.
- **Não tem** → cai para a câmera pura com o avatar renderizado por cima, sem
  mensagem de erro. O visitante nem percebe.

O código da cena (carregar `.glb`, tocar animação, iluminar, animar o texto) é
**idêntico nos dois casos** — muda só a origem da câmera. O custo extra é pequeno
e garante que **funciona em qualquer celular no dia do evento**.

### 4.3 Detalhes da cena

- **Entrada:** avatar surge com fade-in + leve escala (0.8 → 1.0) em ~600 ms
- **Posição:** ~2 m à frente da câmera, na altura dos olhos, virado para o usuário
- **Sempre de frente:** rotação travada no eixo Y para o avatar encarar o usuário
  mesmo se ele circular em volta *(configurável — desligar se quiser que o visitante
  possa vê-lo de lado e de costas)*
- **Iluminação:** luz ambiente + direcional; no modo ARCore, *Light Estimation*
  para casar com a luz real do ambiente
- **Sem sombra de chão** — não há superfície de referência; o avatar flutua
- **Toque na tela:** reposiciona o avatar à frente da câmera
- **Pinça:** ajusta a escala

### 4.4 Texto "Venha nos conhecer!!"

Implementação: **overlay 2D em Compose** sobre a câmera — nitidez perfeita, sempre
legível, custo zero de performance.

| Parâmetro | Valor sugerido |
|---|---|
| Trajetória | De 15% abaixo da base até 15% acima do topo |
| Duração | ~4 s por ciclo |
| Fade | Entra nos primeiros 15%, sai nos últimos 25% |
| Repetição | Infinita, sem pausa |
| Estilo | Fonte pesada, branco com leve glow/sombra — legível sobre qualquer fundo |
| Acento | Ponto ou traço em `#FDB92E` |

*(Alternativa "texto flutuando no espaço 3D" fica registrada como opção, mas
serrilha e some quando a câmera vira — não recomendo.)*

---

## 5. Identidade visual

Cores extraídas diretamente de `imagens/logo.png`:

| Papel | Hex | Uso |
|---|---|---|
| **Verde-petróleo (primária)** | `#00897C` | Barra superior, botões, abas ativas |
| **Amarelo-ouro (acento)** | `#FDB92E` | CTA, badge do EVENTO, botão da RA |
| **Azul-marinho** | `#28146E` | Títulos, alta hierarquia |
| **Azul institucional** | `#005484` | Links, elementos secundários |
| **Cinza** | `#848688` | Texto de apoio, divisores |
| **Grafite** | `#373435` | Corpo de texto |
| Branco / off-white | `#FFFFFF` / `#EFEFEF` | Fundos |

**Diretriz (do `info.txt`):** predominância do verde, **sem exagero**. Verde para
estrutura e navegação, fundos claros, amarelo só em pontos de ação. Tema claro e escuro.

---

## 6. Arquitetura de navegação

```
┌─────────────────────────────────────────────────┐
│  [Logo UFT]        Ester Sabino                 │  ← topo fixo
├─────────────────────────────────────────────────┤
│                                                 │
│                CONTEÚDO DA ABA                  │
│                                                 │
├─────────────────────────────────────────────────┤
│  🏠 Início │ 📋 Projetos │ ⭐ EVENTO │ ℹ️ Saiba Mais │
└─────────────────────────────────────────────────┘
```

### Telas

**Início** — Logo, nome da plataforma, banner clicável do EVENTO, 3 cards resumidos.

**Projetos** — Abas superiores (Projeto 1/2/3) ou lista → tela de detalhe com:
imagem de capa, título, coordenação/equipe, objetivo, público-alvo, local e
período, resultados, galeria de fotos, contato.

**EVENTO** ⭐ — Cabeçalho destacado; qual projeto será apresentado; data, horário
e local; programação; descrição aprofundada; **botão grande "✨ Ver o convite em
RA"**; texto de apoio ("Aponte a câmera para onde quiser").

**Saiba Mais** — Por que "Ester Sabino"; quem é ela; relação com a extensão em
saúde; créditos (equipe, curso, UFT); versão do app.

---

## 7. Stack técnica

| Camada | Escolha | Motivo |
|---|---|---|
| Linguagem | **Kotlin** | Nativo, 100% escrito por mim em texto |
| UI | **Jetpack Compose** (Material 3) | Declarativo, tema simples |
| Navegação | Navigation Compose | Bottom nav + detalhes |
| Câmera | **CameraX** | Preview e captura estáveis |
| 3D / AR | **SceneView** (`io.github.sceneview:arsceneview`, linha 4.x) | API Compose-nativa sobre Filament + ARCore; suporta `.glb` com animação |
| Modelo | **glTF binário (`.glb`)** | Padrão, animação embutida |
| Imagens | Coil | Fotos dos projetos |
| Build | Gradle KTS, minSdk 24 | — |
| Dados | Kotlin/JSON local | **Sem backend — app 100% offline** |

**Removido da v0.1:** ML Kit Barcode Scanning (não há mais QR Code) e toda a
lógica de *Augmented Images*.

**Descartados:** Unity/Vuforia (exige editor gráfico que eu não opero);
Flutter + WebAR (qualidade inferior); 8th Wall (pago).

> **Sem backend** é decisão deliberada: o app funciona mesmo se o Wi-Fi do campus
> cair no dia do evento.

---

## 8. Conteúdo

> **Estratégia definida:** protótipo primeiro, com **conteúdo genérico**;
> os dados reais entram depois, sem mexer no código.

### 8.0 Conteúdo em arquivo separado

Todo o texto fica em **um único arquivo** (`conteudo.kt` ou `conteudo.json`),
isolado da lógica. Trocar "Título 1" pelo nome real do projeto é editar **uma
linha**, sem recompilar nada além do app. É o que torna o "preenchemos depois"
barato de verdade.

### 8.1 Os 3 projetos

Modelo de dados:

```
Projeto {
  id, titulo, subtitulo,
  imagemCapa, galeria[],
  coordenacao, equipe[],
  objetivo, publicoAlvo, metodologia,
  localAtuacao, periodo,
  resultados[], contato
}
```

**Placeholder de cada projeto** (idêntico para os 3, variando o número):

| Campo | Texto do protótipo |
|---|---|
| `titulo` | **Título 1** *(2, 3)* |
| `subtitulo` | Ação extensionista na área da saúde |
| `coordenacao` | Coordenação a definir |
| `equipe` | Equipe formada por docentes e discentes da UFT |
| `objetivo` | Espaço reservado para a descrição do objetivo do projeto: o problema de saúde que ele endereça e o que se pretende alcançar junto à comunidade. |
| `publicoAlvo` | Espaço reservado para a descrição do público atendido pelo projeto. |
| `metodologia` | Espaço reservado para a descrição de como as atividades são conduzidas. |
| `localAtuacao` | Universidade Federal do Tocantins |
| `periodo` | Período a definir |
| `resultados` | Espaço reservado para os resultados alcançados: número de atendimentos, participantes e demais indicadores. |
| `contato` | Contato a definir |
| `imagemCapa` / `galeria` | *Placeholder gráfico* — bloco em verde UFT com a inicial do título |

**Sem imagens reais**, uso um placeholder gerado por código (bloco de cor da
paleta UFT com o número do projeto). Fica limpo e proposital, não parece defeito —
melhor do que ícone de "imagem quebrada".

### 8.1.1 O EVENTO

Definido como **Título 1** (arbitrário; trocar é uma linha).

| Campo | Texto do protótipo |
|---|---|
| Projeto apresentado | Título 1 |
| Data | Data a definir |
| Horário | Horário a definir |
| Local | **Universidade Federal do Tocantins (UFT)** |
| Programação | "O evento contará com uma programação de atividades abertas à comunidade, incluindo apresentação do projeto, rodas de conversa e ações práticas com a equipe. A programação detalhada será divulgada em breve." |
| Chamada da RA | "Toque abaixo e receba nosso convite." |

### 8.2 "Saiba Mais" — a homenagem

Base factual verificada:

- Médica e pesquisadora brasileira do **Instituto de Medicina Tropical da USP (IMT-USP)**;
- **Diretora do IMT-USP entre 2015 e 2019**;
- Professora do Departamento de Moléstias Infecciosas da Faculdade de Medicina da USP;
- Liderou a equipe que **sequenciou o genoma do SARS-CoV-2 no Brasil em menos de
  48 horas** após a notificação do primeiro caso (2020), junto com o **Instituto
  Adolfo Lutz** e a **Universidade de Oxford**;
- O grupo **capacitou mais de 20 laboratórios** pelo país para replicar a técnica;
- Coordena o **CADDE** (*Brazil-UK Centre for Arbovirus Discovery, Diagnosis,
  Genomics and Epidemiology*).

**Gancho narrativo para o texto do nome:** ciência aberta, resposta rápida e
formação de rede — o mesmo que a extensão universitária faz ao levar saúde para
fora dos muros da universidade. A plataforma leva o nome dela porque compartilha
esse método: conhecimento que só vale quando circula.

⚠️ **Confirmem os dados com a coordenação antes da versão final.**

---

## 9. Arquivos que preciso de você

### ✅ Já entregue

| # | Arquivo | Situação |
|---|---|---|
| — | `model_greeting.glb` | **Validado e aprovado** — ver seção 3 |
| — | `imagens/logo.png` | Recebido (348px — serve, mas ver item 3) |

### 🔴 Bloqueantes

| # | Arquivo | Formato | Observação |
|---|---|---|---|
| 1 | **Dados dos 3 projetos** | texto | Título, coordenação, objetivo, público-alvo, período, resultados |
| 2 | **Dados do EVENTO** | texto | Data, horário, local exato, programação, qual dos 3 projetos |

### 🟡 Importantes (uso placeholder, mas piora o resultado)

| # | Arquivo | Observação |
|---|---|---|
| 3 | Logo UFT em alta | `.svg` ou `.png` ≥1024px transparente — a atual tem 348px, pequena para ícone e splash |
| 4 | Fotos dos 3 projetos | 3–6 por projeto, preferencialmente horizontais |
| 5 | Foto de Ester Sabino | **Com licença/crédito de uso** — não posso usar imagem sem autorização |
| 6 | Manual de marca da UFT | Se existir, para fonte e uso correto da logo |

### 🟢 Opcionais

| # | Item | Observação |
|---|---|---|
| 7 | Áudio/locução | Um "vem cá" falado aumenta bastante o impacto |
| 8 | Logo do curso/departamento | Créditos |
| 9 | Mapa do campus | Tela do evento |

**Saiu da lista:** avatar (entregue), QR Code e arte do cartaz (escopo removido),
Blender e Mixamo (não são mais necessários).

### 🔧 Do seu lado (ambiente)

- **Android Studio** + **JDK 17+** (para compilar e gerar o APK)
- **Celular Android físico** — indispensável para testar a câmera e a RA
- Para o modo AR: app *"Google Play Services for AR"* da Play Store
  (lista de aparelhos compatíveis: `developers.google.com/ar/devices`)

---

## 10. Decisões — todas fechadas ✅

| # | Decisão | Definido |
|---|---|---|
| 1 | Nome da plataforma | **Ester Sabino** |
| 2 | QR Code | **Removido** — RA acionada por botão |
| 3 | Ancoragem da RA | **Sem superfície** — avatar no ar, à frente da câmera |
| 4 | Avatar | `model_greeting.glb` — validado |
| 5 | Conteúdo | **Genérico** (Título 1/2/3), estrutura pronta para troca posterior |
| 6 | Projeto do EVENTO | **Título 1** |
| 7 | Local do evento | **UFT** |
| 8 | **Distribuição** | **APK direto** — sem tempo hábil para a Play Store |
| 9 | Material do avatar | **Manter emissivo/holograma** (padrão; reversível por código) |

**Não há mais decisões bloqueantes.** O protótipo pode ser construído por inteiro.

### 10.1 Consequências de "APK direto"

Decidido isso, alguns pontos entram no escopo de entrega:

- **Assinatura:** APK assinado com keystore próprio. **Guardar o keystore** — sem
  ele não dá para publicar atualização como mesmo app;
- **APK único universal** (não *split* por arquitetura), para poder distribuir um
  arquivo só. Fica maior, mas simplifica muito;
- **Instalação de fonte desconhecida:** o Android bloqueia por padrão. Entra no
  pacote um **guia visual de instalação** (3 passos com prints);
- **Distribuição no dia:** QR Code apontando para o APK hospedado (Drive, site da
  UFT ou GitHub Releases) — o QR volta, mas agora só para *baixar o app*, não para RA;
- **Sem atualização automática:** cada nova versão exige reinstalar. Vale
  **congelar a versão alguns dias antes** do evento e testar em 2–3 aparelhos diferentes.

---

## 11. Roadmap de execução

| Fase | Entrega | Situação |
|---|---|---|
| **F0 — Setup** | Projeto Gradle, tema UFT, navegação com 4 abas | ✅ escrito |
| **F1 — Conteúdo** | Telas com placeholder: Início, Projetos, EVENTO, Saiba Mais | ✅ escrito |
| **F2 — Câmera** | Permissão + câmera em tela cheia, abertura pelo botão | ✅ escrito |
| **F3 — Cena 3D** | SceneView sem detecção de plano + fallback automático sem ARCore | ✅ escrito |
| **F4 — Avatar** | `model_greeting.glb` na cena, animação "Greeting" em loop | ✅ escrito |
| **F5 — Texto + captura** | "Venha nos conhecer!!" em loop, foto e compartilhamento | ✅ escrito |
| **F6 — Acabamento** | Ícone, modo escuro, tema de janela | ✅ escrito |
| **F7 — Entrega** | Guia de build, roteiro de teste, wrapper do Gradle | ✅ escrito |
| **F8 — Conteúdo real** *(depois)* | Trocar placeholders pelos textos e fotos verdadeiros | ⏳ aguarda dados |

> ✅ **Compila.** JDK 17 e SDK Android foram instalados na máquina e o projeto
> foi construído: `BUILD SUCCESSFUL` em debug e release, **zero avisos do
> Kotlin**, **Android Lint sem erros**. O APK foi aberto e verificado — o `.glb`
> sai íntegro, o `FileProvider` resolve, o ARCore está como `optional`.
>
> ⚠️ **O que segue não verificado é o comportamento em tela.** Compilar prova
> que o código está correto, não que o avatar aparece do tamanho certo e virado
> para o lado certo. Isso só um celular físico responde.

O protótipo entregue é **F0 → F7**: app completo, navegável, com a RA
implementada e conteúdo genérico.

### 11.1 Bug encontrado pelo lint

O Android Lint pegou um erro real que teria quebrado o app no evento:
`PixelCopy.request` sobre uma `Window` exige **API 26**, não 24 — a sobrecarga
da API 24 é a que recebe uma `SurfaceView`. A captura de foto teria estourado em
qualquer aparelho com Android 7. Corrigido: o botão da foto simplesmente não
aparece nesses aparelhos, e o convite segue funcionando.

---

## 12. Riscos e mitigações

| Risco | Impacto | Mitigação |
|---|---|---|
| ~~`.glb` sem animação embutida~~ | — | ✅ **Resolvido** — arquivo validado (seção 3) |
| ~~Esqueletos incompatíveis / Mixamo fora do ar~~ | — | ✅ **Resolvido** — não dependemos mais do Mixamo |
| Textura de 3,9 MB deixa a abertura da RA lenta | Médio | Comprimir para KTX2/Basis ou reduzir para 1024px; medir tempo de carga em aparelho modesto |
| Avatar emissivo destoa do ambiente | Baixo | Decisão consciente (3.1); gerar as duas versões e comparar no celular |
| Celular do visitante sem ARCore | Baixo | Fallback automático já previsto (5.2) |
| Modelo pesado demais, app travando | Baixo | 35k triângulos está confortável; testar em aparelho modesto |
| Wi-Fi do campus cair | Baixo | App 100% offline |
| Instalação de APK bloqueada pelo Android | Médio | Guia de "fonte desconhecida" + QR de download do APK |
| ~~Conteúdo real atrasar~~ | — | ✅ **Neutralizado** — protótipo com placeholders é entregável por si só |
| Foto de Ester Sabino sem licença | Baixo | Ilustração própria ou imagem de licença livre |
| Perder o keystore de assinatura | Médio | Versionar em local seguro — sem ele não há atualização do mesmo app (10.1) |
| Só testar em um aparelho | Médio | Testar em 2–3 celulares diferentes, com e sem ARCore, antes do evento |

---

## 13. Próximo passo

**O protótipo está pronto e compilando** (F0 → F7). Existe um APK instalável
agora: `app/build/outputs/apk/debug/app-debug.apk`.

O que falta é seu:

1. **Instalar num celular físico e testar** — `adb install -r <apk>`, seguindo o
   roteiro de 12 passos do README. A RA não funciona em emulador.
2. **Conferir o giro do avatar.** Se aparecer de costas, é uma constante:
   `AJUSTE_GIRO_GRAUS` de `0f` para `180f` em `CenaAvatar.kt`. Não deu para
   determinar isso pelo arquivo — glTF não define um "para frente". Alternativa
   sem recompilar: dois dedos giram o avatar na tela.
3. **Conferir escala e distância** do avatar, ajustáveis por constantes no mesmo
   arquivo.

Depois, sem pressa: trocar os textos genéricos pelos reais (F8, um arquivo só),
enviar as fotos e a logo em alta, e criar o keystore de assinatura.

---

### Fontes consultadas

- [SceneView — 3D & AR SDK para Android](https://github.com/sceneview/sceneview)
- [ARCore — dispositivos compatíveis](https://developers.google.com/ar/devices)
- [Mixamo](https://www.mixamo.com)
- [Blender — exportação glTF 2.0](https://docs.blender.org/manual/en/latest/addons/import_export/scene_gltf2.html)
- [Ester Sabino — Wikipédia](https://pt.wikipedia.org/wiki/Ester_Sabino)
- [Jornal da USP — sequenciamento em 48 horas](https://jornal.usp.br/ciencias/ciencias-da-saude/tecnologia-que-sequenciou-coronavirus-em-48-horas-permitira-monitorar-epidemia-em-tempo-real/)
- [Instituto Butantan — entrevista com Ester Sabino](https://butantan.gov.br/noticias/experiencia-do-brasil-com-zika-e-dengue-acelerou-sequenciamento-do-sars-cov-2-no-inicio-da-pandemia-diz-imunologista-ester-sabino)
