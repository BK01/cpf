/*
 * Copyright © 2008-2016, Province of British Columbia
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.bc.gov.open.cpf.plugin.api.test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import ca.bc.gov.open.cpf.plugin.api.BusinessApplicationPlugin;

@BusinessApplicationPlugin(
  name = "StructuredToOpaque",
  version = "1.0.0",
  perRequestResultData = true)
public class StructuredToOpaque {

  private OutputStream resultData;

  private String resultDataContentType;

  public void execute() {
    try {
      final ObjectOutputStream out = new ObjectOutputStream(resultData);
      out.writeObject("test");
      out.close();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void setResultData(final OutputStream resultData) {
    this.resultData = resultData;
  }

  public void setResultDataContentType(final String format) {
    this.resultDataContentType = format;
  }
}
