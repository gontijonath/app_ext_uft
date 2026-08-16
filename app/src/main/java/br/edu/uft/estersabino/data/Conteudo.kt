package br.edu.uft.estersabino.data

/**
 * TODO O TEXTO DO APP ESTÁ NESTE ARQUIVO.
 *
 * Esta é a única coisa que precisa mudar quando os dados reais dos projetos
 * chegarem. Nenhuma tela contém texto fixo — todas leem daqui.
 */
object Conteudo {

    const val NOME_PLATAFORMA = "Ester Sabino"
    const val NOME_GRUPO = "Grupo Ester Sabino"
    const val SUBTITULO_PLATAFORMA = "Ações de Extensão da UFT"
    const val UNIVERSIDADE = "Universidade Federal do Tocantins"

    // ---------------------------------------------------------------- Início

    const val INICIO_SAUDACAO = "Bem-vindo à plataforma"
    const val INICIO_APRESENTACAO =
        "Esta plataforma reúne ações de extensão desenvolvidas na Universidade Federal " +
            "do Tocantins. Navegue pelas abas para conhecer cada projeto."
    const val INICIO_CHAMADA_PROJETOS = "Conheça os projetos"

    // -------------------------------------------------------------- Projetos

    val projetos: List<Projeto> = listOf(
        Projeto(
            id = "projeto-1",
            titulo = "CESAU",
            sigla = "CESAU",
            subtitulo = "Clínica-Escola de Especialidades em Saúde: Ensino e Cuidado Integrados",
            legendaFoto = "Área de recepção da Cesau, no Câmpus de Palmas. Foto: Coren-TO, " +
                "cobertura da inauguração (30/03/2026).",
            sobre = "Inaugurada em 30 de março de 2026 no Câmpus de Palmas, a Cesau " +
                "(Clínica-Escola de Especialidades em Saúde) é a mais nova estrutura de " +
                "extensão em saúde da UFT: 1.480 m² de área construída em dois pavimentos, " +
                "com 33 consultórios, salas de teleconsulta, auditório, espaços de " +
                "simulação clínica, sala de imunização, curativos e central de " +
                "esterilização. O funcionamento é gradual e ocorre somente mediante " +
                "agendamento (não é pronto-socorro nem porta aberta), com atendimentos " +
                "realizados por estudantes dos cursos de Medicina, Enfermagem e Nutrição " +
                "(com colaboração do curso de Psicologia do Câmpus de Miracema) sob " +
                "supervisão docente, nas áreas de clínica médica, pediatria, ginecologia e " +
                "obstetrícia, saúde mental, infectologia, vacinação, testagem e " +
                "acompanhamento nutricional. A criação da Cesau amplia a integração da UFT " +
                "com o Sistema Único de Saúde (SUS) e é um passo relevante rumo ao futuro " +
                "Hospital Universitário de Palmas, cuja área já foi definida em parceria " +
                "com a Prefeitura. Para a comunidade de Palmas, representa mais acesso a " +
                "atendimento especializado gratuito; para a UFT e os cursos de saúde, é " +
                "campo de estágio supervisionado e de projetos de extensão " +
                "interdisciplinares; e para a turma visitante, é a chance de conhecer uma " +
                "estrutura hospitalar-escola de referência recém-entregue e as " +
                "possibilidades de carreira nas profissões de saúde.",
            contatos = listOf(
                "Instagram" to "@uftoficial (perfil institucional da UFT, que divulga a " +
                    "programação da Cesau)",
                "Local" to "Câmpus de Palmas, UFT, Quadra 109 Norte, ALCNO-14, Avenida " +
                    "NS-15, Bloco Bala I, Palmas/TO, CEP 77001-090",
                "Telefone" to "(63) 3229-4683 (central de agendamento presencial do " +
                    "Câmpus de Palmas)",
            ),
            observacao = "Por ser uma unidade recém-inaugurada (mar/2026), a Cesau ainda " +
                "não possui e-mail e Instagram próprios amplamente divulgados; o " +
                "agendamento específico por curso deve ser confirmado com as coordenações " +
                "de Medicina, Enfermagem e Nutrição. Fontes: portal Coren-TO, " +
                "jornaldotocantins.com.br, ojornal.net e coluna Cleber Toledo, links na " +
                "seção \"Fontes consultadas\", em Saiba Mais.",
        ),
        Projeto(
            id = "projeto-2",
            titulo = "CEPIC",
            sigla = "CEPIC",
            subtitulo = "Centro de Práticas Integrativas e Complementares em Saúde",
            legendaFoto = "Foto: reprodução do Instagram do Cepic (@cepic.uft).",
            sobre = "O Cepic é um projeto de extensão da UFT em funcionamento desde 2021 " +
                "no Câmpus de Palmas, hoje consolidado como referência em Práticas " +
                "Integrativas e Complementares em Saúde (PICS). Coordenado pela professora " +
                "Ana Edith Farias Lima, do colegiado do curso de Enfermagem, o centro " +
                "oferece atendimentos gratuitos de massoterapia, auriculoterapia, " +
                "reflexologia podal, reiki, yoga, barra de access e cromoterapia, além de " +
                "testagem rápida e gratuita para infecções sexualmente transmissíveis " +
                "(HIV, sífilis e hepatites B e C), realizados por estudantes de " +
                "Enfermagem, Medicina, Nutrição e Psicologia sob supervisão docente. Os " +
                "atendimentos, com dias e horários fixos por terapia, são abertos tanto à " +
                "comunidade acadêmica quanto ao público externo de Palmas, mediante " +
                "agendamento prévio por telefone ou e-mail. Para a comunidade, o Cepic " +
                "amplia o acesso a cuidados preventivos e de bem-estar; para a UFT, " +
                "fortalece a extensão universitária integrada ao SUS; para os estudantes " +
                "de graduação da área da saúde, é campo de prática supervisionada em " +
                "terapias integrativas pouco exploradas na formação tradicional; e para a " +
                "turma visitante, é uma introdução a abordagens de cuidado em saúde que " +
                "complementa a visita aos demais espaços de saúde do câmpus, mostrando a " +
                "diversidade de atuação possível dentro dos cursos da área.",
            contatos = listOf(
                "Instagram" to "@cepic.uft",
                "E-mail" to "cepic@uft.edu.br",
                "Local" to "Câmpus de Palmas, UFT, prédio anexo à Biblioteca, Quadra 109 " +
                    "Norte, Avenida NS-15, ALCNO-14, Palmas/TO, CEP 77001-090",
                "Telefone" to "(63) 3229-4528",
            ),
        ),
        Projeto(
            id = "projeto-3",
            titulo = "Museu de Morfologia da UFT",
            sigla = "Morfologia",
            subtitulo = "Ciência, Anatomia e Fauna Silvestre ao Alcance de Todos",
            legendaFoto = "Foto: reprodução do Instagram do Museu de Morfologia " +
                "(@morfologiauft).",
            sobre = "O Museu de Morfologia é um projeto de extensão da UFT vinculado ao " +
                "Laboratório de Anatomia Humana do Câmpus de Palmas, criado em 2018 por " +
                "professores e pesquisadores da instituição. Funciona como um espaço de " +
                "educação não formal dedicado à divulgação científica em anatomia humana e " +
                "fauna silvestre, reunindo um acervo com mais de 50 exemplares (peças " +
                "taxidermizadas, material conservado em formol, bancos de tecidos e " +
                "esqueletos), incluindo mais de 40 espécimes de primatas neotropicais dos " +
                "gêneros Sapajus, Alouatta e Callithrix nativos do Tocantins. As visitas " +
                "são gratuitas, voltadas a turmas de escolas municipais e estaduais e a " +
                "grupos universitários, e são conduzidas por estudantes de graduação da " +
                "UFT sob orientação docente (coordenação da professora Tainá de Abreu). " +
                "Para agendar, a escola ou o grupo deve enviar e-mail para " +
                "morfologia@uft.edu.br ou para o canal de visitas ao câmpus " +
                "(visiteocampus@uft.edu.br), informando a data desejada com alguns dias de " +
                "antecedência; o contato também pode ser feito por WhatsApp. O espaço é " +
                "acessível, com maquetes táteis, textos em Braille, sinalização tátil e " +
                "intérprete de Libras. Desde a criação do projeto, ações como a Mostra de " +
                "Morfologia já reuniram mais de 500 visitantes em uma única edição. Para a " +
                "UFT, o museu fortalece a articulação entre ensino, pesquisa e extensão; " +
                "para os estudantes de graduação, é campo de prática de monitoria e " +
                "divulgação científica; e para a turma visitante, é uma oportunidade rara " +
                "de contato direto com peças reais de anatomia humana e da fauna silvestre " +
                "do Tocantins, aproximando o ensino médio da rotina universitária e " +
                "estimulando o interesse por carreiras nas áreas da saúde e das ciências " +
                "biológicas.",
            contatos = listOf(
                "Instagram" to "@morfologiauft",
                "E-mail" to "morfologia@uft.edu.br",
                "Local" to "Avenida NS 15, ALCNO-14, Quadra 109 Norte, Bloco G, sala 1, " +
                    "Palmas/TO, CEP 77001-090",
                "WhatsApp" to "(63) 9 9257-2424",
            ),
        ),
    )

    fun projetoPorId(id: String): Projeto? = projetos.firstOrNull { it.id == id }

    // ---------------------------------------------------------------- Evento

    val evento = Evento(
        // A turma visita o Museu de Morfologia entre os três projetos.
        projetoId = "projeto-3",
        selo = "EVENTO",
        chamada = "Apresentação presencial do projeto",
        data = "Data a definir",
        horario = "Horário a definir",
        local = UNIVERSIDADE,
        localDetalhe = "O local exato dentro do campus será divulgado junto com a " +
            "programação detalhada.",
        programacao = "O evento contará com uma programação de atividades abertas à " +
            "comunidade, incluindo a apresentação do projeto, rodas de conversa com a " +
            "equipe e ações práticas voltadas ao público participante. A programação " +
            "detalhada será divulgada em breve.",
        convite = "Toque abaixo e receba nosso convite.",
    )

    /**
     * Frase que sobe em loop na tela de realidade aumentada. Cita o nome do
     * projeto visitado — se o projeto do evento mudar, o convite acompanha
     * sozinho, sem precisar editar a tela de RA.
     */
    fun textoConviteRa(): String {
        val nomeProjeto = projetoPorId(evento.projetoId)?.titulo ?: evento.chamada
        return "Vem conhecer o $nomeProjeto com a gente! 🦴✨"
    }

    // ------------------------------------------------------------- Saiba Mais

    const val SAIBA_TITULO_NOME = "Por que \"Ester Sabino\"?"
    const val SAIBA_TEXTO_NOME =
        "Esta plataforma leva o nome de Ester Sabino porque compartilha o método dela: " +
            "conhecimento científico só cumpre seu papel quando circula. A extensão " +
            "universitária faz exatamente isso: leva a saúde produzida dentro da " +
            "universidade para fora dos seus muros, e traz de volta as perguntas que a " +
            "comunidade faz."

    const val SAIBA_TITULO_QUEM = "Quem é Ester Sabino"
    const val SAIBA_TEXTO_QUEM =
        "Médica e pesquisadora brasileira do Instituto de Medicina Tropical da " +
            "Universidade de São Paulo (IMT-USP), do qual foi diretora entre 2015 e 2019. " +
            "É professora do Departamento de Moléstias Infecciosas da Faculdade de " +
            "Medicina da USP."

    val saibaFeitos: List<String> = listOf(
        "Liderou a equipe que sequenciou o genoma do SARS-CoV-2 no Brasil em menos de 48 " +
            "horas após a notificação do primeiro caso, em 2020, em trabalho conjunto com " +
            "o Instituto Adolfo Lutz e a Universidade de Oxford.",
        "O grupo capacitou mais de 20 laboratórios pelo país para replicar a técnica, " +
            "criando uma rede nacional de vigilância genômica.",
        "Coordena o CADDE (Brazil-UK Centre for Arbovirus Discovery, Diagnosis, Genomics " +
            "and Epidemiology).",
    )

    const val SAIBA_TITULO_LIGACAO = "A ligação com a extensão"
    const val SAIBA_TEXTO_LIGACAO =
        "Ciência aberta, resposta rápida e formação de rede: os três traços que marcam a " +
            "trajetória homenageada são os mesmos que sustentam uma ação extensionista em " +
            "saúde. Não basta produzir o conhecimento: é preciso compartilhá-lo, aplicá-lo " +
            "onde ele faz falta e ensinar outras pessoas a fazer o mesmo."

    const val SAIBA_AVISO_DADOS =
        "Os dados biográficos acima devem ser conferidos com a coordenação antes da " +
            "versão final da plataforma."

    const val SAIBA_TITULO_CREDITOS = "Créditos"
    val saibaCreditos: List<Pair<String, String>> = listOf(
        "Instituição" to UNIVERSIDADE,
        "Curso" to "Nutrição",
        "Período" to "1º período",
    )

    const val SAIBA_TITULO_FONTES = "Fontes consultadas"

    /** Cada projeto (pelo título) e as fontes usadas pra escrever o texto sobre ele. */
    val fontesConsultadas: List<Pair<String, List<Pair<String, String>>>> = listOf(
        "Museu de Morfologia" to listOf(
            "Cadastro Nacional de Museus (IBRAM)" to
                "https://cadastro.museus.gov.br/museus/museu-de-morfologia-da-universidade-federal-do-tocantins-uft/",
            "Visite Museus (IBRAM)" to
                "https://visite.museus.gov.br/instituicoes/museu-de-morfologia-da-universidade-federal-do-tocantins-uft/",
            "Instagram do museu" to "https://www.instagram.com/morfologiauft/",
            "Portal UFT: V Mostra de Morfologia" to
                "https://www.uft.edu.br/noticias/v-mostra-de-morfologia-da-uft-reune-ciencia-inclusao-e-encantamento-no-campus-de-palmas",
            "Portal UFT: acervo do museu" to
                "https://www.uft.edu.br/noticias/museu-de-morfologia-apresenta-parte-do-seu-acervo",
        ),
        "Cesau, Clínica-Escola de Especialidades em Saúde" to listOf(
            "O Jornal: inauguração da Cesau" to
                "https://ojornal.net/2026/03/26/clinica-escola-de-especialidades-em-saude-da-uft-sera-inaugurada-no-dia-30-de-marco/",
            "Jornal do Tocantins: como funciona a Cesau" to
                "https://www.jornaldotocantins.com.br/cidades/clinica-escola-da-uft-tera-atendimento-a-populac-o-em-palmas-saiba-como-funciona-1.3393460",
            "Coluna Cleber Toledo: inauguração" to
                "https://clebertoledo.com.br/tocantins/uft-inaugura-clinica-escola-de-especialidades-em-saude-na-segunda-feira/",
            "Coren-TO: cobertura da inauguração (fonte da foto)" to
                "https://www.corentocantins.org.br/coren-to-participa-da-inauguracao-da-clinica-escola-de-especialidades-em-saude-da-uft/",
        ),
        "Cepic, Centro de Práticas Integrativas e Complementares" to listOf(
            "Portal UFT: retomada de atividades do Cepic" to
                "https://www.uft.edu.br/noticias/cepic-uft-retoma-atividades-no-segundo-semestre-com-novidades",
            "T1 Notícias: Cepic retoma atividades" to
                "https://t1noticias.com.br/cidades/centro-de-praticas-integrativas-da-uft-retoma-atividades-do-2o-semestre-com-novidades/128258/",
            "Instagram do Cepic" to "https://www.instagram.com/cepic.uft/",
            "Andifes: histórico da UBS/Cepic (desde 2021)" to
                "https://www.andifes.org.br/2021/07/21/uft-unidade-basica-de-saude-da-uft-esta-com-atendimento-aberto-a-comunidade/",
        ),
    )

    const val SAIBA_RODAPE_FONTES =
        "Documento elaborado com base em fontes públicas e oficiais da UFT e de veículos " +
            "de imprensa do Tocantins, consultadas em agosto de 2026. Recomenda-se " +
            "confirmar telefones e horários de agendamento diretamente com cada projeto " +
            "antes da visita, pois informações de contato podem ser atualizadas pela " +
            "instituição."

    const val RODAPE_VERSAO = "Versão de protótipo · conteúdo ilustrativo"
}

data class Projeto(
    val id: String,
    val titulo: String,
    /** Nome curto — usado na aba/pílula de seleção, onde o título completo não cabe. */
    val sigla: String = titulo,
    val subtitulo: String,
    val legendaFoto: String,
    val sobre: String,
    val contatos: List<Pair<String, String>>,
    val observacao: String? = null,
)

data class Evento(
    val projetoId: String,
    val selo: String,
    val chamada: String,
    val data: String,
    val horario: String,
    val local: String,
    val localDetalhe: String,
    val programacao: String,
    val convite: String,
)
