
import express from 'express';
import path from 'path';
import fs from 'fs';
import cors from 'cors';
import logger from 'morgan';
import cookieParser from 'cookie-parser';
import bodyParser  from 'body-parser';
import {connect} from '../database/mongo';
import {picturesRouter} from './routes/api/pictures';
import api from './routes/api';
import views from './routes/views/views';
import {Picture} from '../database/model/Picture';

const server = express();
const HOME = process.env.HOME_URL;

connect().then(() => {
    console.log("configuring server..", path.join(__dirname, 'views'));
    server.set('views', path.join(__dirname, 'views'));
    server.set('view engine', 'hbs');
    server.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));

    server.use(cors());
    server.use(logger('dev'));
    server.use(bodyParser.json());
    server.use(bodyParser.urlencoded({ extended: false }));
    server.use(cookieParser());
    server.use(express.static(path.join(__dirname, 'public')));    
    server.use('/', views);
    server.use('/api', api);
    // catch 404 and forward to error handler
    server.use(function(req, res, next) {
        var err = new Error('Not Found');
        err.status = 404;
        next(err);
    });

    server.use(responseTime());

    // development error handler
    // will print stacktrace
    if (process.env.PRODUCTION === false) {
        server.use((err, req, res, next) => {
            res.status(err.status || 500);
            res.render('error', {
                message: err.message,
                error: err
            });
        });
    } else {
      // production error handler
      // no stacktraces leaked to user
      server.use((err, req, res, next) => {
        res.status(err.status || 500);
        res.render('error', {
          message: err.message,
          error: {}
        });
      });
    }

}, () => {
    // fails mongo
    console.error('mongo failed to connect');
});


export { server }
