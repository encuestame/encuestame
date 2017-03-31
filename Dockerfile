FROM node:7.1.0-alpine
MAINTAINER Juan Picado <juanpicado19@gmail.com>
ENV NODE_ENV=production
WORKDIR /usr/local/src
COPY package.json /usr/local/src/package.json
RUN npm install && npm install --silent --save-dev -g \
        babel \
				babel-preset-es2015 \
        babel-cli \
        webpack
COPY .babelrc /usr/local/src/.babelrc
COPY src /usr/local/src/src
COPY index.js /usr/local/src/index.js
RUN npm run compile
EXPOSE 3000
CMD ["node", "index.js"]