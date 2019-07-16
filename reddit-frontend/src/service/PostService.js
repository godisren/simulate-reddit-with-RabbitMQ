import axios from 'axios'

const API_URL = 'http://localhost:8080'

class PostService {
    createPost(postContent){
        return axios.post(`${API_URL}/post`,
            {content:postContent}
        );
    }

    getAllPosts(){
        return axios.get(`${API_URL}/post`);
    }
}

export default new PostService()