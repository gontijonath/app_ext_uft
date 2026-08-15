package br.edu.uft.estersabino.data

/**
 * TODO O TEXTO DO APP ESTÁ NESTE ARQUIVO.
 *
 * Esta é a única coisa que precisa mudar quando os dados reais dos projetos
 * chegarem. Nenhuma tela contém texto fixo — todas leem daqui. Trocar
 * "Título 1" pelo nome verdadeiro do projeto é editar uma linha.
 *
 * Convenção do protótipo: onde o texto real ainda não existe, o placeholder
 * DESCREVE o que deve entrar ali, em vez de usar "lorem ipsum". Assim a equipe
 * consegue preencher sem precisar de instrução externa.
 */
object Conteudo {

    const val NOME_PLATAFORMA = "Ester Sabino"
    const val SUBTITULO_PLATAFORMA = "Ações extensionistas em saúde · UFT"
    const val UNIVERSIDADE = "Universidade Federal do Tocantins"

    // ---------------------------------------------------------------- Início

    const val INICIO_SAUDACAO = "Bem-vindo à plataforma"
    const val INICIO_APRESENTACAO =
        "Esta plataforma reúne ações de extensão da área da saúde desenvolvidas na " +
            "Universidade Federal do Tocantins. Navegue pelas abas para conhecer cada " +
            "projeto, ver os detalhes do evento de apresentação e entender a homenagem " +
            "que dá nome ao aplicativo."
    const val INICIO_CHAMADA_PROJETOS = "Conheça os projetos"

    // -------------------------------------------------------------- Projetos

    val projetos: List<Projeto> = listOf(
        projetoPlaceholder(1),
        projetoPlaceholder(2),
        projetoPlaceholder(3),
    )

    fun projetoPorId(id: String): Projeto? = projetos.firstOrNull { it.id == id }

    /**
     * Gera um projeto de protótipo. Ao substituir pelos dados reais, troque esta
     * função por três declarações explícitas de [Projeto].
     */
    private fun projetoPlaceholder(numero: Int) = Projeto(
        id = "projeto-$numero",
        titulo = "Título $numero",
        subtitulo = "Ação extensionista na área da saúde",
        coordenacao = "Coordenação a definir",
        equipe = "Equipe formada por docentes e discentes da UFT",
        objetivo = "Espaço reservado para a descrição do objetivo do projeto: o problema " +
            "de saúde que ele endereça, o que se pretende alcançar junto à comunidade e " +
            "de que forma a universidade se aproxima do território atendido.",
        publicoAlvo = "Espaço reservado para a descrição do público atendido pelo projeto — " +
            "faixa etária, território, serviços de saúde envolvidos e número estimado de " +
            "pessoas alcançadas.",
        metodologia = "Espaço reservado para a descrição de como as atividades são " +
            "conduzidas: periodicidade, formatos de encontro, materiais utilizados e " +
            "articulação com a rede de saúde local.",
        localAtuacao = UNIVERSIDADE,
        periodo = "Período a definir",
        resultados = listOf(
            "Espaço reservado para o primeiro resultado alcançado.",
            "Espaço reservado para indicadores: número de atendimentos e participantes.",
            "Espaço reservado para desdobramentos: publicações, parcerias ou continuidade.",
        ),
        contato = "Contato a definir",
    )

    // ---------------------------------------------------------------- Evento

    val evento = Evento(
        // Trocar por outro id muda qual projeto aparece em destaque na aba EVENTO.
        projetoId = "projeto-1",
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
        descricao = "Espaço reservado para o detalhamento aprofundado do projeto que será " +
            "apresentado no evento: contexto, trajetória da ação, impacto na comunidade e " +
            "o que o visitante vai encontrar no dia.",
        convite = "Toque abaixo e receba nosso convite.",
    )

    // ------------------------------------------------------------- Saiba Mais

    const val SAIBA_TITULO_NOME = "Por que \"Ester Sabino\"?"
    const val SAIBA_TEXTO_NOME =
        "Esta plataforma leva o nome de Ester Sabino porque compartilha o método dela: " +
            "conhecimento científico só cumpre seu papel quando circula. A extensão " +
            "universitária faz exatamente isso — leva a saúde produzida dentro da " +
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
        "Coordena o CADDE — Brazil-UK Centre for Arbovirus Discovery, Diagnosis, Genomics " +
            "and Epidemiology.",
    )

    const val SAIBA_TITULO_LIGACAO = "A ligação com a extensão"
    const val SAIBA_TEXTO_LIGACAO =
        "Ciência aberta, resposta rápida e formação de rede: os três traços que marcam a " +
            "trajetória homenageada são os mesmos que sustentam uma ação extensionista em " +
            "saúde. Não basta produzir o conhecimento — é preciso compartilhá-lo, aplicá-lo " +
            "onde ele faz falta e ensinar outras pessoas a fazer o mesmo."

    const val SAIBA_AVISO_DADOS =
        "Os dados biográficos acima devem ser conferidos com a coordenação antes da " +
            "versão final da plataforma."

    const val SAIBA_TITULO_CREDITOS = "Créditos"
    val saibaCreditos: List<Pair<String, String>> = listOf(
        "Instituição" to UNIVERSIDADE,
        "Curso" to "Curso a definir",
        "Equipe" to "Equipe a definir",
        "Orientação" to "Orientação a definir",
    )

    const val RODAPE_VERSAO = "Versão de protótipo · conteúdo ilustrativo"
}

data class Projeto(
    val id: String,
    val titulo: String,
    val subtitulo: String,
    val coordenacao: String,
    val equipe: String,
    val objetivo: String,
    val publicoAlvo: String,
    val metodologia: String,
    val localAtuacao: String,
    val periodo: String,
    val resultados: List<String>,
    val contato: String,
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
    val descricao: String,
    val convite: String,
)
