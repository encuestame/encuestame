<div class="mobile-HashTags tags">
    <c:forEach items="${hashTags}" var="tag">
        <span class="mobile-optionTag"> <a
            href="<%=request.getContextPath()%>/tag/${tag.hashTagName}"
            class="tag">${tag}</a>
        </span>
    </c:forEach>
</div>