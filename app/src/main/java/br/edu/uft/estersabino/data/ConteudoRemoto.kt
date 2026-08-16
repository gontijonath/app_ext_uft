package br.edu.uft.estersabino.data

import android.util.Log
import br.edu.uft.estersabino.ui.comum.MembroEquipe
import br.edu.uft.estersabino.ui.comum.equipeGrupo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

private const val TAG = "ConteudoRemoto"
private const val TIMEOUT_MS = 5000

/**
 * Busca o conteúdo mais atual no Supabase e substitui os textos padrão de
 * [Conteudo]. Chamada uma vez, em [br.edu.uft.estersabino.MainActivity.onCreate].
 *
 * Cada tabela é buscada e aplicada de forma independente: se uma falhar
 * (sem internet, tabela vazia, Supabase ainda não configurado em
 * [SupabaseConfig], campo com nome errado etc.), as outras continuam
 * tentando, e a que falhou simplesmente mantém o texto padrão que já estava
 * em [Conteudo]. O app nunca trava nem mostra tela em branco por causa
 * desta busca — na pior das hipóteses, ele fica com o texto de hoje.
 */
suspend fun carregarConteudoRemoto() {
    if (SupabaseConfig.URL.isBlank() || SupabaseConfig.ANON_KEY.isBlank()) {
        // Supabase ainda não configurado (ver SupabaseConfig.kt) — segue com os padrões.
        return
    }
    withContext(Dispatchers.IO) {
        runCatching { aplicarConteudoGeral(buscarLinhaUnica("conteudo_geral")) }
            .onFailure { Log.w(TAG, "Falha ao buscar conteudo_geral, mantendo padrão", it) }

        runCatching { aplicarEvento(buscarLinhaUnica("evento")) }
            .onFailure { Log.w(TAG, "Falha ao buscar evento, mantendo padrão", it) }

        runCatching { aplicarProjetos(buscarTabela("projetos")) }
            .onFailure { Log.w(TAG, "Falha ao buscar projetos, mantendo padrão", it) }

        runCatching { aplicarEquipe(buscarTabela("equipe")) }
            .onFailure { Log.w(TAG, "Falha ao buscar equipe, mantendo padrão", it) }
    }
}

/** Busca todas as linhas de uma tabela. */
private fun buscarTabela(tabela: String): JSONArray {
    val url = URL("${SupabaseConfig.URL}/rest/v1/$tabela?select=*")
    val conexao = (url.openConnection() as HttpURLConnection).apply {
        requestMethod = "GET"
        connectTimeout = TIMEOUT_MS
        readTimeout = TIMEOUT_MS
        setRequestProperty("apikey", SupabaseConfig.ANON_KEY)
        setRequestProperty("Authorization", "Bearer ${SupabaseConfig.ANON_KEY}")
    }
    try {
        val codigo = conexao.responseCode
        if (codigo !in 200..299) {
            throw java.io.IOException("HTTP $codigo ao buscar $tabela")
        }
        val corpo = conexao.inputStream.bufferedReader().use { it.readText() }
        return JSONArray(corpo)
    } finally {
        conexao.disconnect()
    }
}

/** Tabelas de linha única (`conteudo_geral`, `evento`): pega o primeiro registro. */
private fun buscarLinhaUnica(tabela: String): JSONObject? {
    val linhas = buscarTabela(tabela)
    return if (linhas.length() > 0) linhas.getJSONObject(0) else null
}

private fun JSONObject.stringOu(chave: String, padrao: String): String =
    if (has(chave) && !isNull(chave)) getString(chave) else padrao

private fun JSONObject.stringOuNulo(chave: String): String? =
    if (has(chave) && !isNull(chave)) getString(chave) else null

/** Lê um array `jsonb` de objetos `{"rotulo": "...", "valor": "..."}` como `List<Pair<String,String>>`. */
private fun JSONArray.paresRotuloValor(): List<Pair<String, String>> =
    (0 until length()).map { i ->
        val item = getJSONObject(i)
        item.getString("rotulo") to item.getString("valor")
    }

private fun JSONArray.listaDeStrings(): List<String> =
    (0 until length()).map { i -> getString(i) }

private fun aplicarConteudoGeral(linha: JSONObject?) {
    val l = linha ?: return
    Conteudo.NOME_PLATAFORMA = l.stringOu("nome_plataforma", Conteudo.NOME_PLATAFORMA)
    Conteudo.NOME_GRUPO = l.stringOu("nome_grupo", Conteudo.NOME_GRUPO)
    Conteudo.SUBTITULO_PLATAFORMA = l.stringOu("subtitulo_plataforma", Conteudo.SUBTITULO_PLATAFORMA)
    Conteudo.UNIVERSIDADE = l.stringOu("universidade", Conteudo.UNIVERSIDADE)

    Conteudo.INICIO_SAUDACAO = l.stringOu("inicio_saudacao", Conteudo.INICIO_SAUDACAO)
    Conteudo.INICIO_APRESENTACAO = l.stringOu("inicio_apresentacao", Conteudo.INICIO_APRESENTACAO)
    Conteudo.INICIO_CHAMADA_PROJETOS = l.stringOu("inicio_chamada_projetos", Conteudo.INICIO_CHAMADA_PROJETOS)

    Conteudo.SAIBA_TITULO_NOME = l.stringOu("saiba_titulo_nome", Conteudo.SAIBA_TITULO_NOME)
    Conteudo.SAIBA_TEXTO_NOME = l.stringOu("saiba_texto_nome", Conteudo.SAIBA_TEXTO_NOME)
    Conteudo.SAIBA_TITULO_QUEM = l.stringOu("saiba_titulo_quem", Conteudo.SAIBA_TITULO_QUEM)
    Conteudo.SAIBA_TEXTO_QUEM = l.stringOu("saiba_texto_quem", Conteudo.SAIBA_TEXTO_QUEM)
    if (l.has("saiba_feitos") && !l.isNull("saiba_feitos")) {
        Conteudo.saibaFeitos = l.getJSONArray("saiba_feitos").listaDeStrings()
    }
    Conteudo.SAIBA_TITULO_LIGACAO = l.stringOu("saiba_titulo_ligacao", Conteudo.SAIBA_TITULO_LIGACAO)
    Conteudo.SAIBA_TEXTO_LIGACAO = l.stringOu("saiba_texto_ligacao", Conteudo.SAIBA_TEXTO_LIGACAO)
    Conteudo.SAIBA_AVISO_DADOS = l.stringOu("saiba_aviso_dados", Conteudo.SAIBA_AVISO_DADOS)

    Conteudo.SAIBA_TITULO_CREDITOS = l.stringOu("saiba_titulo_creditos", Conteudo.SAIBA_TITULO_CREDITOS)
    if (l.has("saiba_creditos") && !l.isNull("saiba_creditos")) {
        Conteudo.saibaCreditos = l.getJSONArray("saiba_creditos").paresRotuloValor()
    }

    Conteudo.SAIBA_TITULO_FONTES = l.stringOu("saiba_titulo_fontes", Conteudo.SAIBA_TITULO_FONTES)
    if (l.has("fontes_consultadas") && !l.isNull("fontes_consultadas")) {
        val grupos = l.getJSONArray("fontes_consultadas")
        Conteudo.fontesConsultadas = (0 until grupos.length()).map { i ->
            val grupo = grupos.getJSONObject(i)
            val projeto = grupo.getString("projeto")
            val fontes = grupo.getJSONArray("fontes")
            val pares = (0 until fontes.length()).map { j ->
                val fonte = fontes.getJSONObject(j)
                fonte.getString("rotulo") to fonte.getString("url")
            }
            projeto to pares
        }
    }
    Conteudo.SAIBA_RODAPE_FONTES = l.stringOu("saiba_rodape_fontes", Conteudo.SAIBA_RODAPE_FONTES)
    Conteudo.RODAPE_VERSAO = l.stringOu("rodape_versao", Conteudo.RODAPE_VERSAO)
}

private fun aplicarEvento(linha: JSONObject?) {
    val l = linha ?: return
    val atual = Conteudo.evento
    Conteudo.evento = Evento(
        projetoId = l.stringOu("projeto_id", atual.projetoId),
        selo = l.stringOu("selo", atual.selo),
        chamada = l.stringOu("chamada", atual.chamada),
        data = l.stringOu("data", atual.data),
        horario = l.stringOu("horario", atual.horario),
        local = l.stringOu("local", atual.local),
        localDetalhe = l.stringOu("local_detalhe", atual.localDetalhe),
        programacao = l.stringOu("programacao", atual.programacao),
        convite = l.stringOu("convite", atual.convite),
    )
}

private fun aplicarProjetos(linhas: JSONArray) {
    if (linhas.length() == 0) return
    val atuais = Conteudo.projetos.associateBy { it.id }
    val novos = (0 until linhas.length()).map { i ->
        val l = linhas.getJSONObject(i)
        val id = l.getString("id")
        val atual = atuais[id]
        Projeto(
            id = id,
            titulo = l.stringOu("titulo", atual?.titulo ?: id),
            sigla = l.stringOu("sigla", atual?.sigla ?: id),
            subtitulo = l.stringOu("subtitulo", atual?.subtitulo ?: ""),
            legendaFoto = l.stringOu("legenda_foto", atual?.legendaFoto ?: ""),
            sobre = l.stringOu("sobre", atual?.sobre ?: ""),
            contatos = if (l.has("contatos") && !l.isNull("contatos")) {
                l.getJSONArray("contatos").paresRotuloValor()
            } else {
                atual?.contatos ?: emptyList()
            },
            observacao = l.stringOuNulo("observacao") ?: atual?.observacao,
        )
    }
    // Só substitui a lista inteira se todos os projetos padrão continuarem
    // presentes — evita que uma tabela incompleta no Supabase derrube um
    // projeto inteiro da tela de Projetos.
    if (novos.map { it.id }.toSet() == atuais.keys) {
        Conteudo.projetos = novos
    }
}

/**
 * Só `nome` e `papel` vêm do Supabase — foto e cor continuam vindo do app,
 * casadas pelo mesmo [MembroEquipe.id] (ver comentário na definição).
 */
private fun aplicarEquipe(linhas: JSONArray) {
    if (linhas.length() == 0) return
    val atuais = equipeGrupo.associateBy { it.id }
    val novos = (0 until linhas.length()).map { i ->
        val l = linhas.getJSONObject(i)
        val id = l.getString("id")
        val atual = atuais[id] ?: return
        atual.copy(
            nome = l.stringOu("nome", atual.nome),
            papel = l.stringOu("papel", atual.papel),
        )
    }
    if (novos.map { it.id }.toSet() == atuais.keys) {
        equipeGrupo = novos
    }
}
