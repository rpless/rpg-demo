package io.github.rpless.rpg

import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen.Choose
import org.scalatest.prop.{Checkers, GeneratorDrivenPropertyChecks}
import org.scalatest.{FlatSpec, Matchers}

trait RpgSpec extends FlatSpec with Matchers with Checkers with GeneratorDrivenPropertyChecks {
  val Width = 100.0
  val Height = 100.0

  val vectorGen: Gen[Vector2] = for {
    x <- Choose.chooseDouble.choose(0, Width)
    y <- Choose.chooseDouble.choose(0, Height)
  } yield Vector2(x, y)

  val playerGen: Gen[Player] = for {
    id <- Gen.uuid
    position <- vectorGen
    direction <- vectorGen
  } yield Player(id, position, direction)


  implicit def arbitraryVector = Arbitrary(vectorGen)
  implicit def arbitraryPlayer = Arbitrary(playerGen)
}
