/*******************************************************************************
 * Copyright 2012 University of Southern California
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This code was developed by the Information Integration Group as part 
 * of the Karma project at the Information Sciences Institute of the 
 * University of Southern California.  For more information, publications, 
 * and related projects, please see: http://www.isi.edu/integration
 ******************************************************************************/

package edu.isi.karma.rdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.isi.karma.kr2rml.KR2RMLRDFWriter;
import edu.isi.karma.util.EncodingDetector;
import edu.isi.karma.webserver.KarmaException;


/**
 * @author dipsy
 * 
 */
public abstract class TestJSONRDFGenerator extends TestRdfGenerator{

	protected GenericRDFGenerator rdfGen;
	private static Logger logger = LoggerFactory.getLogger(TestJSONRDFGenerator.class);
	
	protected void executeBasicJSONTest(String filename, String modelName, boolean generateProvenance, int expectedNumberOfLines) throws IOException, URISyntaxException,
			KarmaException {
		logger.info("Loading json file: " + filename);
		String jsonData = EncodingDetector.getString(new File(getTestResource(filename).toURI()),
				"utf-8");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		List<KR2RMLRDFWriter> writers = this.createBasicWriter(pw);
		rdfGen.generateRDF(modelName, filename, jsonData, generateProvenance, writers);
		String rdf = sw.toString();
		assertNotEquals(rdf.length(), 0);
		String[] lines = rdf.split(System.getProperty("line.separator"));
		assertEquals(expectedNumberOfLines, lines.length);
	}

	protected URL getTestResource(String name)
	{
		return getClass().getClassLoader().getResource(name);
	}
}
