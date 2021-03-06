/*
 * Copyright 2017 Fs2 Rabbit
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gvolpe.fs2rabbit.util

import cats.effect.Sync
import org.slf4j.LoggerFactory

trait Log[F[_]] {
  def info(value: String): F[Unit]
  def error(error: Throwable): F[Unit]
}

object Log {
  private[fs2rabbit] val logger = LoggerFactory.getLogger(this.getClass)

  implicit def syncLogInstance[F[_]](implicit F: Sync[F]): Log[F] =
    new Log[F] {
      override def error(error: Throwable): F[Unit] = F.delay(logger.error(error.getMessage, error))
      override def info(value: String): F[Unit]     = F.delay(logger.info(value))
    }
}
