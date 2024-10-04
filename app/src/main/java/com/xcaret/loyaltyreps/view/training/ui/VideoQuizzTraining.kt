package com.xcaret.loyaltyreps.view.training.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.xcaret.loyaltyreps.R
import com.xcaret.loyaltyreps.adapter.QuestionFragment
import com.xcaret.loyaltyreps.adapter.QuestionsViewPagerAdapter
import com.xcaret.loyaltyreps.data.api.ResponseQuestionChoice
import com.xcaret.loyaltyreps.data.api.VideoQuizQuestion
import com.xcaret.loyaltyreps.data.api.VideoQuizTraining
import com.xcaret.loyaltyreps.databinding.FragmentVideoQuizzTrainingBinding
import com.xcaret.loyaltyreps.view.base.BaseFragmentDataBinding
import com.xcaret.loyaltyreps.view.base.ServerErrorDialogFragment
import com.xcaret.loyaltyreps.view.training.vm.VideoQuizzViewModel

class VideoQuizzTraining:  BaseFragmentDataBinding<FragmentVideoQuizzTrainingBinding>(){
    override val tagForBar: String
        get() = "VideoQuizzFragment"
    lateinit var _viewModel: VideoQuizzViewModel
    lateinit var wallet: String
    lateinit var videoId: String
    lateinit var pointsQuiz: String
    lateinit var nameQuiz: String

    var quizzSuccess: Boolean = true
    override fun setHeaderFragment()  {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wallet = arguments?.getString("wallet")!!
        videoId = arguments?.getString("video_id")!!

        _viewModel = ViewModelProvider(this)[VideoQuizzViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setBinding(FragmentVideoQuizzTrainingBinding.inflate(inflater))
        setHeaderFragment()
        observers()
        _viewModel.fetchVideoTrainingData(videoId)
        return binding.root
    }

    private fun setupUi(videoQuiz: VideoQuizTraining) {
        val points: Int = videoQuiz.points
        pointsQuiz = points.toString()
        nameQuiz = videoQuiz.name

        var videoInstructions = "Ganarás <b>$points puntos</b> al responder el quiz correctamente.\n"
        videoInstructions += "Lee con atención las preguntas y selecciona la respuesta correcta."
        binding.txtInfoQuiz.text = HtmlCompat.fromHtml(videoInstructions, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.txtTitleQuiz.text = videoQuiz.name
    }

    private fun settingUpUi(questions: List<VideoQuizQuestion>) {
        Log.i("QuestionFragment", "onViewCreated: ${questions.size}")
        val fragments: MutableList<QuestionFragment> = mutableListOf()
        for (question in questions){
            fragments.add(QuestionFragment(question,::questionSelected))
        }

        val adapter = QuestionsViewPagerAdapter(fragments, this)
        binding.fpQuizQuestions.adapter = adapter
        binding.fpQuizQuestions.isUserInputEnabled = false
        binding.quizProgress.max = questions.size
        binding.fpQuizQuestions.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.quizProgress.progress = position + 1
            }
        })
    }
    private fun questionSelected(res: ResponseQuestionChoice){
        val position = binding.fpQuizQuestions.currentItem
        if(!res.is_correct){
            quizzSuccess = false
        }
        if(finalFormPager()){
            binding.fpQuizQuestions.setCurrentItem(position + 1, true)
        }else{
            if(quizzSuccess){
                Log.i("QuestionFragment", "Final respuesta quiz success !!!! $res")
                _viewModel.addUserQuiz(wallet,videoId,pointsQuiz, "Quiz - $nameQuiz",{
                    _parentActivity?.supportFragmentManager?.beginTransaction()
                        ?.add(ServerErrorDialogFragment.newInstance(), null)
                        ?.commitAllowingStateLoss()
                }){
                    sendSuccessResultView()
                }
            }else{
                sendFailedResultView()
                Log.i("QuestionFragment", "Final respuesta quiz failed !!!! $res")
            }
        }
    }
    fun sendSuccessResultView(){
        val bundle = Bundle()
        bundle.putString("name_quizz", nameQuiz)
        bundle.putString("points_quizz", pointsQuiz)

        navigate(R.id.action_videoQuizzTraining_to_quizzSuccessTraining, bundle)
    }
    fun sendFailedResultView(){
        val bundle = Bundle()
        bundle.putString("video_id", videoId)
        navigate(R.id.action_videoQuizzTraining_to_quizzFailedTraining, bundle)
    }
    private fun finalFormPager(): Boolean{
        return binding.fpQuizQuestions.currentItem < _viewModel.videoQuizTraining.value!!.questions.count() -1
    }
    private fun observers() {
        _viewModel.videoQuizTraining.observe(viewLifecycleOwner){
            it?.let{ videoQuiz ->
                videoQuiz.questions.let { questions ->
                    settingUpUi(questions)
                }
                setupUi(videoQuiz)
            }
        }
    }

}