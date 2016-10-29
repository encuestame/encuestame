/**
 * Created by jpicado on 27/08/16.
 */
import mongoose from 'mongoose';

const connect = () => {
    console.log("mongo database", process.env.MONGO_URL);
    return new Promise(function(resolve, reject) {
        const options = {
            db: {
                native_parser: true
            },
            server: { poolSize: 1 },
        };
        mongoose.connect(process.env.MONGO_URL || null, options);
        const db = mongoose.connection;
        db.on('error', function(error) {
            console.error('mongo fails', error);
            reject(error);
        });
        db.once('open', function() {
            console.log('mongo connected !!!');
            resolve(db);
        });
    });

};


export {connect}