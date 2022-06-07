import nextConnect from 'next-connect';

export default function handler(req, res) {
    if (req.method === 'GET') {
      // Process a POST request
      console.log('Call api success');
    } else {
        console.log('Call api success');
    }
  }