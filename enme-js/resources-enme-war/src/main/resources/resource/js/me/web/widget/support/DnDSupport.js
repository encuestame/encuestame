define([
     "dojo",
     "dojo/_base/declare",
     "dojo/dom-attr",
     "dojo/_base/lang",
     "me/core/features/base",
     "me/core/enme"],
    function(
      dojo,
      declare,
      domAttr,
      lang,
      featuresHas,
      _ENME) {

   var default_effect = "copy",
   data_attr = 'data-id',
   drag_options = {
      // default data attr id
      attr : 'data-id',
      // default effect drop
      effectDrop: default_effect,
      // triggered on drag
      onDrag: function() {},
      // triggered on start drag
      onDragStart: function() {},
      // triggered on drag end
      onDragEnd: function() {}
    },
    // target options
    target_options ={
      // default data attr id
      attr : 'data-id',
      cssClassOver: '',
      // default effect drop
      effectDrop: default_effect,
      // triggered on enter to the drop zone
      onDragEnter : function(){},
      // triggered when the node is over the drop zone
      onDragOver: function(){},
      // triggered when the source leave the drop zone
      onDragLeave: function(){},
      // triggered on drop inside the zone
      onDrop: function() {}
  };

  return declare(null, {

    /**
     * Check if the client has drag and drop support
     * @property isDnD
     */
    isDnD : false,

    /**
     *
     * @property DnDManager
     */
    DnDManager : null,

    /**
     * Private array to store connect event handlers.
     * @property _events
     */
    _events  : [],

   /**
    * Constructor.
    */
    constructor : function() {
        this.isDnD = featuresHas.draganddrop || false;
    },

    /**
     *
     * @method dragItems
     */
    dragItem : function(node, options) {
      var target_id = this.id;
      if (node) {
          options = lang.mixin(drag_options, options);
          node.draggable = true;
          var _node = node;
          //event  on drag start

          //  The ondragstart event is fired when the user starts to drag the selection.
          var on_start = function(event) {
              //event.preventDefault();
              node.style.opacity = '0.4';
              event.dataTransfer.effectAllowed = default_effect;
              // The dataTransfer property is key to the drag-and-drop API
              var dt = event.dataTransfer;
              dt.setData("item-id", domAttr.get(_node, options.attr));
              dt.setData("target-id", target_id);
              options.onDragStart(event, _node, options);
               //setCursor(_node);
          };

          // The ondragend event is fired when the user has finished the drag operation (released the mouse button or pressed ESC).
          var dragend = function (event) {
              node.style.opacity = '1';
              options.onDragEnd(event, _node, options);
              //cleanCursor(_node);
          };

          // The ondrag event is fired periodically during the drag operation.
          var drag = function(event) {
              //event.preventDefault();
              options.onDrag(event, _node, options);
          };

          node.addEventListener('dragstart', on_start, false);
          node.addEventListener('drag', drag, false);
          node.addEventListener('dragend', dragend, false);
      }
    },

    /**
     * Add D&D target support within node.
     * @param node
     * @param options
     * @method dropTarget
     */
    dropTarget : function(node, options) {
        var _node = node;
       if (node) {
          node.draggable = false;
          options = lang.mixin(target_options, options);
          // The ondragenter event is fired when the user moves the mouse pointer into the element.
          var dragenter = function(event) {
              event.dataTransfer.dropEffect = 'none';
              dojo.addClass(node, options.cssClassOver || '');
              event.preventDefault();
          };
          // The ondragover event is fired periodically while the mouse pointer is over the element.
          var dragover = function(event) {
              event.dataTransfer.dropEffect = default_effect;
              if (event.preventDefault) {
                  event.preventDefault(); // Necessary. Allows us to drop.
              }
              options.onDragOver(event, _node, options);
              return false;
          };
          // The ondragleave event is fired when the user moves the mouse pointer out of the element.
          var dragleave = function(event) {
              dojo.removeClass(_node, options.cssClassOver || '');
              options.onDragLeave(event, _node, options);
          };
          // The ondrop event is fired when the dragged data is dropped on the element.
          var drop = function(e) {
              if (e.preventDefault) {
                  e.preventDefault(); // Necessary. Allows us to drop.
              }
              dojo.removeClass(_node, options.cssClassOver || '');
              var dt = e.dataTransfer;
              var value = e.dataTransfer.getData("item-id"),
              target_id = e.dataTransfer.getData("target-id");
              options.onDrop(this, dt, target_id, value, _node, options);
          };
          // list of events
          node.addEventListener('dragenter', dragenter, false);
          node.addEventListener('dragover', dragover, false);
          node.addEventListener('dragleave', dragleave, false);
          node.addEventListener('drop', drop, false);
      }
    }

  });
});