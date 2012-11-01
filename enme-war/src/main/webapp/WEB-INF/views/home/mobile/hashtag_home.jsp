 <div class="mobile-home-subtitle">
    Hot HashTags
  </div>
    <div class="mobile-HashTags">
        <c:forEach items="${hashTags}" var="tag">
            <span class="mobile-optionTag"><a href="<%=request.getContextPath()%>/tag/${tag.hashTagName}">${tag}</a> |</span>
        </c:forEach>
    <div class="mobile-hashTag-cloud"> <a href="<%=request.getContextPath()%>/cloud"> Cloud </a></div>
    </div>