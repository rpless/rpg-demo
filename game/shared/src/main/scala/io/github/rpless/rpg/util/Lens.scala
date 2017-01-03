package io.github.rpless.rpg.util

case class Lens[A, B](g: A => B, s: (B, A) => A) {
  def get(a: A): B = g(a)
  def set(b: B)(a: A) = s(b, a)

  def modify(f: B => B)(a: A): A = set(f(get(a)))(a)

  def andThen[C](lens: Lens[B, C]) = Lens[A, C](
    (a: A) => lens.get(get(a)),
    (c: C, a: A) => modify(b => lens.set(c)(b))(a)
  )
}
