/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.persistence.pojo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * QuestionsAnswersId.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since October 17, 2009
 */
@Embeddable
public class QuestionsAnswersId implements java.io.Serializable {

    private Long idAnswers;
    private Long qid;

    @Column(name = "id_answers", nullable = false)
    public Long getIdAnswers() {
        return this.idAnswers;
    }

    public void setIdAnswers(Long idAnswers) {
        this.idAnswers = idAnswers;
    }

    @Column(name = "qid", nullable = false)
    public Long getQid() {
        return this.qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof QuestionsAnswersId))
            return false;
        QuestionsAnswersId castOther = (QuestionsAnswersId) other;

        return (this.getIdAnswers() == castOther.getIdAnswers())
                && (this.getQid() == castOther.getQid());
    }

    public int hashCode() {
        int result = 17;

        result = (int) (37 * result + this.getIdAnswers());
        result = (int) (37 * result + this.getQid());
        return result;
    }

}
