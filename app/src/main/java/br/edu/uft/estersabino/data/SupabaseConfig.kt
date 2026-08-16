package br.edu.uft.estersabino.data

/**
 * Credenciais do projeto Supabase que guarda o conteúdo editável do app
 * (ver `supabase/schema.sql` para o script de criação das tabelas).
 *
 * A ANON_KEY aqui é a chave *pública* do Supabase — feita para ir dentro do
 * app. Com Row Level Security ativado nas tabelas e só a política de
 * leitura liberada (como o `schema.sql` configura), essa chave só permite
 * ler o conteúdo, nunca escrever. Não é segredo, mas também não custa nada
 * mantê-la aqui em vez de espalhada pelo código.
 *
 * Enquanto URL/ANON_KEY estiverem em branco, [br.edu.uft.estersabino.data.carregarConteudoRemoto]
 * não faz nada — o app segue com os textos padrão já escritos em
 * [Conteudo]. Preencha os dois depois de criar o projeto no Supabase
 * (Settings → API → Project URL / anon public key).
 */
object SupabaseConfig {
    const val URL = ""
    const val ANON_KEY = ""
}
