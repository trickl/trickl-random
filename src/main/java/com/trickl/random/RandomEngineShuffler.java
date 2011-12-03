/*
 * This file is part of the Trickl Open Source Libraries.
 *
 * Trickl Open Source Libraries - http://open.trickl.com/
 *
 * Copyright (C) 2011 Tim Gee.
 *
 * Trickl Open Source Libraries are free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Trickl Open Source Libraries are distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this project.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.trickl.random;

import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public class RandomEngineShuffler implements Shuffler {

   private RandomEngine randomEngine = new MersenneTwister();

   public RandomEngineShuffler() {
   }

   public RandomEngineShuffler(RandomEngine randomEngine) {
      this.randomEngine = randomEngine;
   }

   @Override
   public void shuffle(List<?> list) {
      // Modified from the Apache Software Implementation,
      // this code is under the ASF license
      // http://www.apache.org/licenses/LICENSE-2.0
      @SuppressWarnings("unchecked") // we won't put foreign objects in
      final List<Object> objectList = (List<Object>) list;

      Uniform uniform = new Uniform(randomEngine);
      if (list instanceof RandomAccess) {
         for (int i = objectList.size() - 1; i > 0; i--) {
            int index = uniform.nextIntFromTo(0, i);
            objectList.set(index, objectList.set(i, objectList.get(index)));
         }
      } else {
         Object[] array = objectList.toArray();
         for (int i = array.length - 1; i > 0; i--) {
            int index = uniform.nextIntFromTo(0, i);
            Object temp = array[i];
            array[i] = array[index];
            array[index] = temp;
         }

         int i = 0;
         ListIterator<Object> it = objectList.listIterator();
         while (it.hasNext()) {
            it.next();
            it.set(array[i++]);
         }
      }
   }

   public RandomEngine getRandomEngine() {
      return randomEngine;
   }

   public void setRandomEngine(RandomEngine randomEngine) {
      this.randomEngine = randomEngine;
   }
}
