package br.edu.uft.estersabino.ui.ar

import com.google.ar.core.Camera
import com.google.ar.core.Pose
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Parâmetros da cena do convite, reunidos para poderem ser ajustados sem caçar
 * números soltos pelo código.
 */
object CenaAvatar {

    /** Arquivo em `app/src/main/assets/`. */
    const val ARQUIVO_MODELO = "model_greeting.glb"

    /**
     * Nome da animação embutida no .glb, confirmado na inspeção do arquivo:
     * uma única animação chamada "Greeting", de 4,83 s, que já emenda em loop.
     */
    const val ANIMACAO = "Greeting"

    /** Altura final do avatar, em metros. O modelo original tem 1,897 m. */
    const val ALTURA_METROS = 1.75f

    /** Distância à frente da câmera onde o avatar é plantado. */
    const val DISTANCIA_METROS = 2.0f

    /** Altura presumida do celular em relação ao chão, ao ser empunhado. */
    const val ALTURA_CELULAR_METROS = 1.45f

    /**
     * Giro extra aplicado ao avatar, em graus.
     *
     * O cálculo abaixo deixa o eixo +Z do modelo apontando para a câmera. Se no
     * teste em aparelho o avatar aparecer de costas, troque para 180. Não foi
     * possível determinar isso pelo arquivo: glTF não define um "para frente",
     * e este modelo veio do exportador do Three.js.
     *
     * Alternativa sem recompilar: o avatar é editável por gestos — dois dedos
     * giram o modelo na hora.
     */
    const val AJUSTE_GIRO_GRAUS = 0f
}

/**
 * Calcula onde plantar o avatar: a [CenaAvatar.DISTANCIA_METROS] metros à frente
 * de quem segura o celular, no nível do chão e virado para a pessoa.
 *
 * Não há detecção de plano aqui — a pose é derivada só da câmera. É o que
 * permite o convite aparecer no instante do toque, sem pedir que o visitante
 * varra o chão com o celular primeiro.
 */
fun poseNaFrenteDaCamera(camera: Camera): Pose {
    val poseCamera = camera.pose

    // Eixo Z negativo da câmera = direção para onde ela aponta.
    val frente = poseCamera.getTransformedAxis(2, -1f)

    // Projeta no plano horizontal: se a pessoa aponta para o chão ou para o
    // teto, o avatar mesmo assim nasce em pé, à frente dela.
    var dx = frente[0]
    var dz = frente[2]
    val norma = sqrt(dx * dx + dz * dz)
    if (norma > 1e-4f) {
        dx /= norma
        dz /= norma
    } else {
        // Câmera apontada quase na vertical: usa o "para frente" do aparelho.
        dx = 0f
        dz = -1f
    }

    val x = poseCamera.tx() + dx * CenaAvatar.DISTANCIA_METROS
    val z = poseCamera.tz() + dz * CenaAvatar.DISTANCIA_METROS
    val y = poseCamera.ty() - CenaAvatar.ALTURA_CELULAR_METROS

    // Gira em torno de Y para encarar quem está olhando.
    val anguloParaCamera = atan2(poseCamera.tx() - x, poseCamera.tz() - z)
    val angulo = anguloParaCamera + Math.toRadians(CenaAvatar.AJUSTE_GIRO_GRAUS.toDouble()).toFloat()

    val quaternion = floatArrayOf(0f, sin(angulo / 2f), 0f, cos(angulo / 2f))
    return Pose(floatArrayOf(x, y, z), quaternion)
}
