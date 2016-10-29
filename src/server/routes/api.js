import express from 'express';

const router = express.Router();

router.get('/status', function(req, res) {
  res.json({
      date: Date.now()
  })
});

export default router;

