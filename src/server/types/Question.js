import {
  GraphQLObjectType,
  GraphQLInt,
  GraphQLNonNull,
  GraphQLID,
  GraphQLString,
} from 'graphql';

let QuestionType = new GraphQLObjectType({
  name: 'Question',
  fields: () => ({
     _id: {
      type: new GraphQLNonNull(GraphQLID)
    },
    name: {
      type: GraphQLString,
      description: 'the question name',
    },
    type: {
      type: GraphQLString,
      description: 'question type',
    }
  })
});

export default Question;