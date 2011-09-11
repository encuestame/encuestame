--
-- TOC entry 2094 (class 1259 OID 51309)
-- Dependencies: 1686
-- Name: emailindex; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX emailindex ON useraccount USING btree (email);


--
-- TOC entry 2103 (class 1259 OID 51310)
-- Dependencies: 1686
-- Name: usernameindex; Type: INDEX; Schema: public; Owner: postgres; Tablespace:
--

CREATE INDEX usernameindex ON useraccount USING btree (username);