-- Ester Sabino — conteúdo editável do app (Supabase)
--
-- Rode este script uma vez, inteiro, no SQL Editor do Supabase (projeto
-- vazio, recém-criado). Ele cria as 3 tabelas que o app lê ao abrir
-- (ConteudoRemoto.kt), ativa Row Level Security com leitura pública e
-- escrita bloqueada (só quem está logado no painel do Supabase edita), e já
-- insere o texto real de hoje — então o app funciona igual ao de antes assim
-- que a URL/chave forem preenchidas em SupabaseConfig.kt, e a partir daí dá
-- pra editar qualquer texto direto pela aba "Table Editor" do Supabase, sem
-- mexer em código nem publicar nada de novo na Play Store.
--
-- Depois de rodar: Settings → API → copie "Project URL" e a chave "anon
-- public", e mande as duas para preencher SupabaseConfig.kt.

-- =============================================================== conteudo_geral
-- Uma linha só, com todos os textos "fixos" do app (fora dos projetos e do evento).

create table conteudo_geral (
    id bigint generated always as identity primary key,
    nome_plataforma text not null,
    nome_grupo text not null,
    subtitulo_plataforma text not null,
    universidade text not null,

    inicio_saudacao text not null,
    inicio_apresentacao text not null,
    inicio_chamada_projetos text not null,

    saiba_titulo_nome text not null,
    saiba_texto_nome text not null,
    saiba_titulo_quem text not null,
    saiba_texto_quem text not null,
    saiba_feitos jsonb not null,
    saiba_titulo_ligacao text not null,
    saiba_texto_ligacao text not null,
    saiba_aviso_dados text not null,
    saiba_titulo_creditos text not null,
    saiba_creditos jsonb not null,
    saiba_titulo_fontes text not null,
    fontes_consultadas jsonb not null,
    saiba_rodape_fontes text not null,
    rodape_versao text not null
);

alter table conteudo_geral enable row level security;

create policy "conteudo_geral: leitura pública"
    on conteudo_geral for select
    to anon
    using (true);

insert into conteudo_geral (
    nome_plataforma, nome_grupo, subtitulo_plataforma, universidade,
    inicio_saudacao, inicio_apresentacao, inicio_chamada_projetos,
    saiba_titulo_nome, saiba_texto_nome,
    saiba_titulo_quem, saiba_texto_quem,
    saiba_feitos,
    saiba_titulo_ligacao, saiba_texto_ligacao,
    saiba_aviso_dados,
    saiba_titulo_creditos, saiba_creditos,
    saiba_titulo_fontes, fontes_consultadas,
    saiba_rodape_fontes, rodape_versao
) values (
    'Ester Sabino',
    'Grupo Ester Sabino',
    'Ações de Extensão da UFT',
    'Universidade Federal do Tocantins',

    'Bem-vindo à plataforma',
    'Esta plataforma reúne ações de extensão desenvolvidas na Universidade Federal do Tocantins. Navegue pelas abas para conhecer cada projeto.',
    'Conheça os projetos',

    'Por que "Ester Sabino"?',
    'Esta plataforma leva o nome de Ester Sabino porque compartilha o método dela: conhecimento científico só cumpre seu papel quando circula. A extensão universitária faz exatamente isso: leva a saúde produzida dentro da universidade para fora dos seus muros, e traz de volta as perguntas que a comunidade faz.',

    'Quem é Ester Sabino',
    'Médica e pesquisadora brasileira do Instituto de Medicina Tropical da Universidade de São Paulo (IMT-USP), do qual foi diretora entre 2015 e 2019. É professora do Departamento de Moléstias Infecciosas da Faculdade de Medicina da USP.',

    '[
        "Liderou a equipe que sequenciou o genoma do SARS-CoV-2 no Brasil em menos de 48 horas após a notificação do primeiro caso, em 2020, em trabalho conjunto com o Instituto Adolfo Lutz e a Universidade de Oxford.",
        "O grupo capacitou mais de 20 laboratórios pelo país para replicar a técnica, criando uma rede nacional de vigilância genômica.",
        "Coordena o CADDE (Brazil-UK Centre for Arbovirus Discovery, Diagnosis, Genomics and Epidemiology)."
    ]'::jsonb,

    'A ligação com a extensão',
    'Ciência aberta, resposta rápida e formação de rede: os três traços que marcam a trajetória homenageada são os mesmos que sustentam uma ação extensionista em saúde. Não basta produzir o conhecimento: é preciso compartilhá-lo, aplicá-lo onde ele faz falta e ensinar outras pessoas a fazer o mesmo.',

    'Os dados biográficos acima devem ser conferidos com a coordenação antes da versão final da plataforma.',

    'Créditos',
    '[
        {"rotulo": "Instituição", "valor": "Universidade Federal do Tocantins"},
        {"rotulo": "Curso", "valor": "Nutrição"},
        {"rotulo": "Período", "valor": "1º período"}
    ]'::jsonb,

    'Fontes consultadas',
    '[
        {
            "projeto": "Museu de Morfologia",
            "fontes": [
                {"rotulo": "Cadastro Nacional de Museus (IBRAM)", "url": "https://cadastro.museus.gov.br/museus/museu-de-morfologia-da-universidade-federal-do-tocantins-uft/"},
                {"rotulo": "Visite Museus (IBRAM)", "url": "https://visite.museus.gov.br/instituicoes/museu-de-morfologia-da-universidade-federal-do-tocantins-uft/"},
                {"rotulo": "Instagram do museu", "url": "https://www.instagram.com/morfologiauft/"},
                {"rotulo": "Portal UFT: V Mostra de Morfologia", "url": "https://www.uft.edu.br/noticias/v-mostra-de-morfologia-da-uft-reune-ciencia-inclusao-e-encantamento-no-campus-de-palmas"},
                {"rotulo": "Portal UFT: acervo do museu", "url": "https://www.uft.edu.br/noticias/museu-de-morfologia-apresenta-parte-do-seu-acervo"}
            ]
        },
        {
            "projeto": "Cesau, Clínica-Escola de Especialidades em Saúde",
            "fontes": [
                {"rotulo": "O Jornal: inauguração da Cesau", "url": "https://ojornal.net/2026/03/26/clinica-escola-de-especialidades-em-saude-da-uft-sera-inaugurada-no-dia-30-de-marco/"},
                {"rotulo": "Jornal do Tocantins: como funciona a Cesau", "url": "https://www.jornaldotocantins.com.br/cidades/clinica-escola-da-uft-tera-atendimento-a-populac-o-em-palmas-saiba-como-funciona-1.3393460"},
                {"rotulo": "Coluna Cleber Toledo: inauguração", "url": "https://clebertoledo.com.br/tocantins/uft-inaugura-clinica-escola-de-especialidades-em-saude-na-segunda-feira/"},
                {"rotulo": "Coren-TO: cobertura da inauguração (fonte da foto)", "url": "https://www.corentocantins.org.br/coren-to-participa-da-inauguracao-da-clinica-escola-de-especialidades-em-saude-da-uft/"}
            ]
        },
        {
            "projeto": "Cepic, Centro de Práticas Integrativas e Complementares",
            "fontes": [
                {"rotulo": "Portal UFT: retomada de atividades do Cepic", "url": "https://www.uft.edu.br/noticias/cepic-uft-retoma-atividades-no-segundo-semestre-com-novidades"},
                {"rotulo": "T1 Notícias: Cepic retoma atividades", "url": "https://t1noticias.com.br/cidades/centro-de-praticas-integrativas-da-uft-retoma-atividades-do-2o-semestre-com-novidades/128258/"},
                {"rotulo": "Instagram do Cepic", "url": "https://www.instagram.com/cepic.uft/"},
                {"rotulo": "Andifes: histórico da UBS/Cepic (desde 2021)", "url": "https://www.andifes.org.br/2021/07/21/uft-unidade-basica-de-saude-da-uft-esta-com-atendimento-aberto-a-comunidade/"}
            ]
        }
    ]'::jsonb,

    'Documento elaborado com base em fontes públicas e oficiais da UFT e de veículos de imprensa do Tocantins, consultadas em agosto de 2026. Recomenda-se confirmar telefones e horários de agendamento diretamente com cada projeto antes da visita, pois informações de contato podem ser atualizadas pela instituição.',
    'Versão de protótipo · conteúdo ilustrativo'
);

-- =========================================================================== evento
-- Uma linha só: dados do evento de apresentação presencial.

create table evento (
    id bigint generated always as identity primary key,
    projeto_id text not null,
    selo text not null,
    chamada text not null,
    data text not null,
    horario text not null,
    local text not null,
    local_detalhe text not null,
    programacao text not null,
    convite text not null
);

alter table evento enable row level security;

create policy "evento: leitura pública"
    on evento for select
    to anon
    using (true);

insert into evento (
    projeto_id, selo, chamada, data, horario, local, local_detalhe, programacao, convite
) values (
    'projeto-3',
    'EVENTO',
    'Apresentação presencial do projeto',
    'Data a definir',
    'Horário a definir',
    'Universidade Federal do Tocantins',
    'O local exato dentro do campus será divulgado junto com a programação detalhada.',
    'O evento contará com uma programação de atividades abertas à comunidade, incluindo a apresentação do projeto, rodas de conversa com a equipe e ações práticas voltadas ao público participante. A programação detalhada será divulgada em breve.',
    'Toque abaixo e receba nosso convite.'
);

-- ========================================================================= projetos
-- Uma linha por projeto (CESAU, CEPIC, Museu de Morfologia).

create table projetos (
    id text primary key,
    titulo text not null,
    sigla text not null,
    subtitulo text not null,
    legenda_foto text not null,
    sobre text not null,
    contatos jsonb not null,
    observacao text
);

alter table projetos enable row level security;

create policy "projetos: leitura pública"
    on projetos for select
    to anon
    using (true);

insert into projetos (id, titulo, sigla, subtitulo, legenda_foto, sobre, contatos, observacao) values
(
    'projeto-1',
    'CESAU',
    'CESAU',
    'Clínica-Escola de Especialidades em Saúde: Ensino e Cuidado Integrados',
    'Área de recepção da Cesau, no Câmpus de Palmas. Foto: Coren-TO, cobertura da inauguração (30/03/2026).',
    'Inaugurada em 30 de março de 2026 no Câmpus de Palmas, a Cesau (Clínica-Escola de Especialidades em Saúde) é a mais nova estrutura de extensão em saúde da UFT: 1.480 m² de área construída em dois pavimentos, com 33 consultórios, salas de teleconsulta, auditório, espaços de simulação clínica, sala de imunização, curativos e central de esterilização. O funcionamento é gradual e ocorre somente mediante agendamento (não é pronto-socorro nem porta aberta), com atendimentos realizados por estudantes dos cursos de Medicina, Enfermagem e Nutrição (com colaboração do curso de Psicologia do Câmpus de Miracema) sob supervisão docente, nas áreas de clínica médica, pediatria, ginecologia e obstetrícia, saúde mental, infectologia, vacinação, testagem e acompanhamento nutricional. A criação da Cesau amplia a integração da UFT com o Sistema Único de Saúde (SUS) e é um passo relevante rumo ao futuro Hospital Universitário de Palmas, cuja área já foi definida em parceria com a Prefeitura. Para a comunidade de Palmas, representa mais acesso a atendimento especializado gratuito; para a UFT e os cursos de saúde, é campo de estágio supervisionado e de projetos de extensão interdisciplinares; e para a turma visitante, é a chance de conhecer uma estrutura hospitalar-escola de referência recém-entregue e as possibilidades de carreira nas profissões de saúde.',
    '[
        {"rotulo": "Instagram", "valor": "@uftoficial (perfil institucional da UFT, que divulga a programação da Cesau)"},
        {"rotulo": "Local", "valor": "Câmpus de Palmas, UFT, Quadra 109 Norte, ALCNO-14, Avenida NS-15, Bloco Bala I, Palmas/TO, CEP 77001-090"},
        {"rotulo": "Telefone", "valor": "(63) 3229-4683 (central de agendamento presencial do Câmpus de Palmas)"}
    ]'::jsonb,
    'Por ser uma unidade recém-inaugurada (mar/2026), a Cesau ainda não possui e-mail e Instagram próprios amplamente divulgados; o agendamento específico por curso deve ser confirmado com as coordenações de Medicina, Enfermagem e Nutrição. Fontes: portal Coren-TO, jornaldotocantins.com.br, ojornal.net e coluna Cleber Toledo, links na seção "Fontes consultadas", em Saiba Mais.'
),
(
    'projeto-2',
    'CEPIC',
    'CEPIC',
    'Centro de Práticas Integrativas e Complementares em Saúde',
    'Foto: reprodução do Instagram do Cepic (@cepic.uft).',
    'O Cepic é um projeto de extensão da UFT em funcionamento desde 2021 no Câmpus de Palmas, hoje consolidado como referência em Práticas Integrativas e Complementares em Saúde (PICS). Coordenado pela professora Ana Edith Farias Lima, do colegiado do curso de Enfermagem, o centro oferece atendimentos gratuitos de massoterapia, auriculoterapia, reflexologia podal, reiki, yoga, barra de access e cromoterapia, além de testagem rápida e gratuita para infecções sexualmente transmissíveis (HIV, sífilis e hepatites B e C), realizados por estudantes de Enfermagem, Medicina, Nutrição e Psicologia sob supervisão docente. Os atendimentos, com dias e horários fixos por terapia, são abertos tanto à comunidade acadêmica quanto ao público externo de Palmas, mediante agendamento prévio por telefone ou e-mail. Para a comunidade, o Cepic amplia o acesso a cuidados preventivos e de bem-estar; para a UFT, fortalece a extensão universitária integrada ao SUS; para os estudantes de graduação da área da saúde, é campo de prática supervisionada em terapias integrativas pouco exploradas na formação tradicional; e para a turma visitante, é uma introdução a abordagens de cuidado em saúde que complementa a visita aos demais espaços de saúde do câmpus, mostrando a diversidade de atuação possível dentro dos cursos da área.',
    '[
        {"rotulo": "Instagram", "valor": "@cepic.uft"},
        {"rotulo": "E-mail", "valor": "cepic@uft.edu.br"},
        {"rotulo": "Local", "valor": "Câmpus de Palmas, UFT, prédio anexo à Biblioteca, Quadra 109 Norte, Avenida NS-15, ALCNO-14, Palmas/TO, CEP 77001-090"},
        {"rotulo": "Telefone", "valor": "(63) 3229-4528"}
    ]'::jsonb,
    null
),
(
    'projeto-3',
    'Museu de Morfologia da UFT',
    'Morfologia',
    'Ciência, Anatomia e Fauna Silvestre ao Alcance de Todos',
    'Foto: reprodução do Instagram do Museu de Morfologia (@morfologiauft).',
    'O Museu de Morfologia é um projeto de extensão da UFT vinculado ao Laboratório de Anatomia Humana do Câmpus de Palmas, criado em 2018 por professores e pesquisadores da instituição. Funciona como um espaço de educação não formal dedicado à divulgação científica em anatomia humana e fauna silvestre, reunindo um acervo com mais de 50 exemplares (peças taxidermizadas, material conservado em formol, bancos de tecidos e esqueletos), incluindo mais de 40 espécimes de primatas neotropicais dos gêneros Sapajus, Alouatta e Callithrix nativos do Tocantins. As visitas são gratuitas, voltadas a turmas de escolas municipais e estaduais e a grupos universitários, e são conduzidas por estudantes de graduação da UFT sob orientação docente (coordenação da professora Tainá de Abreu). Para agendar, a escola ou o grupo deve enviar e-mail para morfologia@uft.edu.br ou para o canal de visitas ao câmpus (visiteocampus@uft.edu.br), informando a data desejada com alguns dias de antecedência; o contato também pode ser feito por WhatsApp. O espaço é acessível, com maquetes táteis, textos em Braille, sinalização tátil e intérprete de Libras. Desde a criação do projeto, ações como a Mostra de Morfologia já reuniram mais de 500 visitantes em uma única edição. Para a UFT, o museu fortalece a articulação entre ensino, pesquisa e extensão; para os estudantes de graduação, é campo de prática de monitoria e divulgação científica; e para a turma visitante, é uma oportunidade rara de contato direto com peças reais de anatomia humana e da fauna silvestre do Tocantins, aproximando o ensino médio da rotina universitária e estimulando o interesse por carreiras nas áreas da saúde e das ciências biológicas.',
    '[
        {"rotulo": "Instagram", "valor": "@morfologiauft"},
        {"rotulo": "E-mail", "valor": "morfologia@uft.edu.br"},
        {"rotulo": "Local", "valor": "Avenida NS 15, ALCNO-14, Quadra 109 Norte, Bloco G, sala 1, Palmas/TO, CEP 77001-090"},
        {"rotulo": "WhatsApp", "valor": "(63) 9 9257-2424"}
    ]'::jsonb,
    null
);

-- =========================================================================== equipe
-- Uma linha por integrante do grupo (carrossel de rosto+papel na tela Início).
-- Só nome e papel são editáveis por aqui — a foto e a cor de cada uma
-- continuam fixas no app, casadas pelo "id".

create table equipe (
    id text primary key,
    nome text not null,
    papel text not null
);

alter table equipe enable row level security;

create policy "equipe: leitura pública"
    on equipe for select
    to anon
    using (true);

insert into equipe (id, nome, papel) values
    ('patricia', 'Patricia Gontijo', 'Facilitadora Digital'),
    ('luisa', 'Luisa Gabrielly', 'Mediadora Virtual'),
    ('ruth', 'Ruth Carvalho', 'Moderadora'),
    ('vivian', 'Vivian Maria', 'Pacificadora'),
    ('isadora', 'Isadora Ribeiro', 'Secretária');
