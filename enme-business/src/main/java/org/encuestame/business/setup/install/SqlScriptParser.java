package org.encuestame.business.setup.install;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Parses a textual SQL script containing a group of database commands separated
 * by semi-colons and converts it into an array of {@link String}s, suitable for
 * processing through JDBC or any other desired mechanism.
 * <p/>
 * This code is based loosely on source code from the <i>Apache Ant</i> project
 * at:
 * <p/>
 * <code>http://ant.apache.org/index.html</code>
 * <p/>
 * As mentioned, multiple statements can be provided, separated by semi-colons.
 * Lines within the script can be commented using either "--" or "//". Comments
 * extend to the end of the line.
 */
public class SqlScriptParser {

    private static Log log = LogFactory.getLog(SqlScriptParser.class);


    /**
     *
     * @param scriptFile
     *            a <code>java.io.File</code> object points to a .sql file to be
     *            parsed.
     * @param stmts
     *            This argument is modified after this method executed. Elements
     *            in <code>stmts</code> are SQL queries in
     *            <code>scriptFile</code> separated by semicolons.
     * @return <code>true</code> if hasn't met with any exceptions during
     *         execution, <code>false</code> if met.
     * @throws IOException
     */
    public static void readScript(File scriptFile, String[] stmts)
            throws IOException {
    	//FIXME: 'reader' is never closed
        BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
        StringBuffer stmtString = new StringBuffer();
        String line = null;

        while (null != (line = reader.readLine())) {
            if (line.length() == 0)
                continue; // omit blank lines
            if (line.startsWith("--"))
                continue; // omit comments
            if ("commit;".equals(line.trim()))
                continue; // omit 'commit' stmt
            stmtString.append(" ").append(line);
            line = null;
        }

        stmts = stmtString.toString().split(";");
    }

    /**
     * Read the SQL script from the specified file.
     *
     * @param scriptFilePath
     *            Full path to the SQL script file.
     * @return A list of SQL statements.
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public static String[] readScript(final String scriptFilePath) throws IOException {
        //BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath));
        log.debug("readScript "+scriptFilePath);
        BufferedReader reader = new BufferedReader(
                       new InputStreamReader(new ClassPathResource(scriptFilePath).getInputStream()));
        log.debug("BufferedReader "+reader);
        String line = null;

        StringBuffer sqlStatements = new StringBuffer();
        List<String> specialStatements = new ArrayList<String>();
        StringTokenizer sqlTokens = null;
        String[] sqlStmnts = null;
        String[] spslSqlStmnts = null;
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("--") && line.length() > 0
                    && !line.startsWith("/") && !line.equals("commit;")) {
                if (line.endsWith("END;") || line.endsWith("end;")
                        || line.contains("&quot;")) {
                    if (line.contains("&quot;")) {
                        line = line.trim().substring(0,
                                line.trim().length() - 1);
                        specialStatements.add(line);
                    } else {
                        specialStatements.add(line);
                    }
                } else {
                    sqlStatements.append(" ");
                    sqlStatements.append(line);
                }
            }
            line = null;
        }

        sqlTokens = new StringTokenizer(sqlStatements.toString(), ";");

        if (sqlTokens != null) {
            if (specialStatements != null) {
                sqlStmnts = new String[sqlTokens.countTokens()
                        + specialStatements.size()];
            } else {
                sqlStmnts = new String[sqlTokens.countTokens()];
            }

            int i = 0;
            while (sqlTokens.hasMoreTokens()) {
                // stores only the first statement
                sqlStmnts[i++] = sqlTokens.nextToken();
            }
            if (specialStatements != null) {
                spslSqlStmnts = (String[]) specialStatements
                        .toArray(new String[specialStatements.size()]);

                for (int j = 0; j < spslSqlStmnts.length; j++) {
                    // stores only the first statement
                    sqlStmnts[i++] = spslSqlStmnts[j];
                }
            }
        }

        return sqlStmnts;
    }
}
