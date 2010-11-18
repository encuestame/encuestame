<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initBody.jsp" %>
<script type="text/javascript">
    dojo.require("dojo.data.ItemFileWriteStore");
    dojo.require("dijit.tree.ForestStoreModel");
    dojo.require("dijit.tree.dndSource");
    dojo.require("dijit.Tree");

      dojo.addOnLoad(function() {
            var store = new dojo.data.ItemFileWriteStore({
                url: "/encuestame/api/admon/location/folders.json"
            });

            var treeModel = new dijit.tree.ForestStoreModel({
                store: store,
                query: {
                    "type": "continent"
                },
                rootId: "root",
                rootLabel: "Continents",
                childrenAttrs: ["children"]
            });

            new dijit.Tree({
                model: treeModel,
                dndController: "dijit.tree.dndSource"
            },
            "treeThree");
        });
</script>

<!-- Temporal Api -->
<script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true_or_false&amp;key=ABQIAAAAZf9tmUBWs70LNEs037RG0RSRDJTW6dAoIXeFoxQMgUw1UOU57BQVW7UAkRLJtL8j7TITUtS2p6eh9g"
        type="text/javascript"></script>
<div>
   <div id="treeThree"></div>
</div>
<%@ include file="/WEB-INF/jsp/includes/endBody.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/validate.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>